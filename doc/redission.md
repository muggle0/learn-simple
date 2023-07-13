前段时间，有小伙伴问我，redission锁的原理，看门狗的作用，和一些实际开发中的场景，当时并没有给他比较完整的解答，后来我查了资料对redission做了一个总结，在这里分享给小伙伴们
## redission 锁的实现原理
以下是redission锁的使用示例：
```
        RLock lock = redisson.getLock ("key");
        lock.lock ();
        try {
          //  do something
        } catch (Exception e) {
           // do something
        }finally {
            lock.unlock ();
        }
```
我们看一下`RLock`实现类`RedissonLock`的lock方法代码：
```
private void lock(long leaseTime, TimeUnit unit, boolean interruptibly) throws InterruptedException {
        long threadId = Thread.currentThread().getId();
        Long ttl = tryAcquire(leaseTime, unit, threadId);   
        // lock acquired
        if (ttl == null) {
            return;
        ......
}
```
追溯`tryAcquire`方法，最终能找到`tryLockInnerAsync`这个方法：
```
<T> RFuture<T> tryLockInnerAsync(long leaseTime, TimeUnit unit, long threadId, RedisStrictCommand<T> command) {
        internalLockLeaseTime = unit.toMillis(leaseTime);

        return evalWriteAsync(getName(), LongCodec.INSTANCE, command,
                "if (redis.call('exists', KEYS[1]) == 0) then " +
                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return nil; " +
                        "end; " +
                        "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return nil; " +
                        "end; " +
                        "return redis.call('pttl', KEYS[1]);",
                Collections.singletonList(getName()), internalLockLeaseTime, getLockName(threadId));
    }
```
不难看出，这个方法是通过lua 脚本进行上锁操作，它首先通过 `exists`判断当前的key是否存在，如果不存在，则使用 `hincrby`命令创建一个新的哈希表，其中哈希表的key 也就是 ARVG[2] 的值的源码：
```
protected String getLockName(long threadId) {
        return id + ":" + threadId;
    }
```
它是当前redission 的id +":"+当前线程的id，再通过`hincrby` 和 `pexpire`初始化当前线程持有的锁。
如果当前key存在，则通过`hexists`判断哈希表中是否有当前线程的锁，如果有则通过`hincrby`指令给哈希表中的值加一，然后通过`pexpire`重置锁过期时间。最后一个return是如果不是当前线程持有锁，则返回当前锁剩余有效时间。
不难看出`RedissonLock`是支持重入的，只要当前线程持有了该锁，下次获取锁的时候通过`hincrby`进行加一操作。
那它是怎么释放锁的呢？我们直接看`org.redisson.RedissonLock#unlockInnerAsync`方法：
```
protected RFuture<Boolean> unlockInnerAsync(long threadId) {
        return evalWriteAsync(getName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN,
                "if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then " +
                        "return nil;" +
                        "end; " +
                        "local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); " +
                        "if (counter > 0) then " +
                        "redis.call('pexpire', KEYS[1], ARGV[2]); " +
                        "return 0; " +
                        "else " +
                        "redis.call('del', KEYS[1]); " +
                        "redis.call('publish', KEYS[2], ARGV[1]); " +
                        "return 1; " +
                        "end; " +
                        "return nil;",
                Arrays.asList(getName(), getChannelName()), LockPubSub.UNLOCK_MESSAGE, internalLockLeaseTime, getLockName(threadId));
    }
```
这个段lua脚本也是先判断当前线程的锁是否存在，不存在直接返回，存在则对哈希表中线程的值减一；如果值大于1 证明该锁被重入，不应该释放，重置过期时间返回，否则删除该锁，然后 调用`publish`指令通知其他订阅了该key的线程重新抢占。
参考我以往博客的lua脚本：
```
StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"setnx\",KEYS[1],ARGV[1])==1 ");
        sb.append("then ");
        sb.append(" redis.call(\"pexpire\",KEYS[1],KEYS[2]) ");
        sb.append(" return 1 ");
        sb.append("else ");
        sb.append(" return 0 ");
        sb.append("end ");
        String script = sb.toString();
        RedisCallback<Boolean> callback = (connection) -> connection.eval(script.getBytes(),
            ReturnType.BOOLEAN, 1, key.getBytes(Charset.forName("UTF-8")),
            value.getBytes(Charset.forName("UTF-8")));
        Boolean execute = redisTemplate.execute(callback);
```
我这个分布式锁是使用redisTemplate 实现的，不支持重入，对比redission就简陋很多。
## 看门狗的原理
在明白看门狗的原理的之前，我们要先了解为什么要看门狗？它的作用是在Redisson实例被关闭前，不断的延长锁的有效期，也就是说，如果一个拿到锁的线程一直没有完成逻辑，那么看门狗会帮助线程不断的延长锁超时时间，锁不会因为超时而被释放。 如果没有看门狗，就会导致业务代码没跑完，锁已经释放的情况，可能你会说那不给锁过期时间不就行了，那如果某个线程释放锁失败，会把整个业务场景锁死，造成生产事故；而有看门狗的情况解锁失败也只会死锁续期的那一段时间，造成的影响远比不设过期时间的情况要小。
在之前提到的`tryAcquireAsync()`方法中如果没有传入过期时间，就会调用`org.redisson.RedissonLock#scheduleExpirationRenewal`方法创建看门狗：
```
private void scheduleExpirationRenewal(long threadId) {
        ExpirationEntry entry = new ExpirationEntry();
        ExpirationEntry oldEntry = EXPIRATION_RENEWAL_MAP.putIfAbsent(getEntryName(), entry);
        if (oldEntry != null) {
            oldEntry.addThreadId(threadId);
        } else {
            entry.addThreadId(threadId);
            renewExpiration();
        }
    }
```
再看 `org.redisson.RedissonLock#renewExpiration`：
```
private void renewExpiration() {
    ExpirationEntry ee = EXPIRATION_RENEWAL_MAP.get(getEntryName());
    if (ee == null) {
        return;
    }
    
    Timeout task = commandExecutor.getConnectionManager().newTimeout(new TimerTask() {
        @Override
        public void run(Timeout timeout) throws Exception {
            ExpirationEntry ent = EXPIRATION_RENEWAL_MAP.get(getEntryName());
            if (ent == null) {
                return;
            }
            Long threadId = ent.getFirstThreadId();
            if (threadId == null) {
                return;
            }
            
            RFuture<Boolean> future = renewExpirationAsync(threadId);
            future.onComplete((res, e) -> {
                if (e != null) {
                    log.error("Can't update lock " + getName() + " expiration", e);
                    return;
                }
                
                if (res) {
                    // reschedule itself
                    renewExpiration();
                }
            });
        }
    }, internalLockLeaseTime / 3, TimeUnit.MILLISECONDS);
    
    ee.setTimeout(task);
}
```
不难看出，看门狗本质就是一个schedule线程，它的执行时间间隔是`internalLockLeaseTime / 3` 也就是锁设置的过期时间的三分之一。
## 一些疑问

1. 如果忘记释放锁，看门狗会给我的锁无限续期吗？

门狗线程的执行逻辑是获取持有当前锁的线程id，然后续期。如果线程id没有被从当前锁的map中剔除，就会一直续期。而剔除线程id的方法是`org.redisson.RedissonLock#cancelExpirationRenewal`，很不幸，这个方法只有在解锁的时候被调用。

2. redission 还有哪些锁，分别是为了解决什么问题

这是一个比较大的问题，在这里只对redission 锁及作用进行简单介绍：

- RedissonRedLock：红锁，用于redis多节点部署架构，它是同时对多个redis节点进行上锁，过半节点上锁成功才算加锁成功，主要是为了防止单节点挂掉导致锁失效
- RedissonMultiLock：联锁，将多个RLock对象关联为一个联锁，提供一个锁合并的功能。
- RedissonSemaphore：信号量，同 JUC中的信号量，底层指令是`decrby`与 `incrby`
