一次redis 使用不当，差点让我提桶跑路

## 前情提要
接到一个需求，功能是新注册的三天内用户，每天的第一次登录会收到提示消息；看到这个需求，脑海中马上浮现出了bitmap的技术方案——
通过bitmap过滤当天已经发送过消息的用户，然后这个rediskey 第二天凌晨过期，重新统计。
由于bitmap的在这个业务场景的误判只会把没收过消息用户误判为收到过了消息，不说这种误判几率很小，即使产生误判对业务影响也几乎不计；
所以从技术上完全可行。

## 代码实现

方案有了，开始撸代码。我是通过lua脚本来实现这个功能的：
```lua
local exisKey = redis.call('exists', KEYS[1]);
if exisKey == 1 then
    local exitMessage = redis.call('getbit', KEYS[1], ARGV[1]);
    return exitMessage;
else
    redis.call('setbit', KEYS[1], 0, 1)
    redis.call('expire', KEYS[1], ARGV[2])
    return 0;
end

```
这个脚本的意思是先检查当前key 是否存在，如果不存在则 `setbit 0 1` 然后把key过期时间设置为凌晨12点过期，并返回当前value
不存在map 中，如果key存在并则返回getmap 的值。最后在消息发送完成会调用`setbit` 的指令把当前用户id写入bitmap中。
在项目中，redis客户端用的是jedis，加载lua脚本的代码写成了这样：

```java
 String shaLua = this.jedisService.scriptLoad(luaContent);

 Object result = this.jedisService.evalsha(shaLua,keys,args);
```
### 代码中的问题

上诉代码看似没有问题，实现了需求，实际上踩了两个大坑。

问题1：redis key过期时间设置在12点，而项目中很多业务都是需要在12点重置，而且都会涉及到redis相关操作。
这种做法存在大量key同时过期的隐患，会影响redis性能给redis造成较大负载。

问题2：this.jedisService.scriptLoad(luaContent) 方法使用不当。对于lua脚本，
只需要在项目启动的时候通过该方法加载一次就够了，这里却是每次判断用户是否存在在bitmap中都要重新加载一次。
这个加载过程会造成redis 集群的停顿，频繁调用会造成较大的负载。而这个业务逻辑还是在用户登录这一块，造成的影响面就很大了。


## 优化

代码刚上线不久，就被领导发现代码中的问题。还好没有暴雷并提前发现了，最终修改了技术方案重构了代码紧急上线。可怜的我瑟瑟发抖，吓出一身冷汗。

新的技术方案：
- 1. key 中带入日期，这样在第二天就不用考虑key过期的问题了，原来的key为`xxx:xxx` 现在的key为：`xxx:xxx:2023_04_28`。
- 2. key 过期时间随机设置为第二天的1点到2点的某个时间点，避免key的集中过期。
- 3.  `jedisService.scriptLoad(luaContent)` 方法修改到 spring bean 的前置处理方法中也就是 `@PostConstruct` 
      注解的方法，该方法会返回一个sha算法得到的md5值，将这个值存起来用于调用lua脚本。