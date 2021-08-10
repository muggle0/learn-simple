package com.example.demo.controller;

import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.Charset;

/**
 * Description
 * Date 2021/8/10
 * Created by muggle
 */
public class AutoCloseRedisLocker implements AutoCloseable {

    private String key;

    private String value;

    private long exrieTime;

    private StringRedisTemplate redisTemplate;

    public AutoCloseRedisLocker(String key, String value, long exrieTime, StringRedisTemplate redisTemplate) {
        this.key = key;
        this.value = value;
        this.exrieTime = exrieTime;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void close() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"GET\",KEYS[1])==ARGV[1] ");
        sb.append("then ");
        sb.append(" redis.call(\"DEL\",KEYS[1]) ");
        sb.append(" return 1 ");
        sb.append("else ");
        sb.append(" return 0 ");
        sb.append("end ");
        String script = sb.toString();
        RedisCallback<Boolean> callback = (connection) -> connection.eval(script.getBytes(),
            ReturnType.BOOLEAN, 2, key.getBytes(Charset.forName("UTF-8")),
            String.valueOf(exrieTime).getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8")));
        Boolean execute = redisTemplate.execute(callback);
        if (!execute){
            throw new IllegalStateException("解锁失败");
        }
    }

    public boolean lock(){
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
        return execute;
    }

    public static AutoCloseRedisLocker buildAndLock(String key,String value,long exrieTime,StringRedisTemplate redisTemplate){
        final AutoCloseRedisLocker autoCloseRedisLocker = new AutoCloseRedisLocker(key, value, exrieTime, redisTemplate);
        final boolean lock = autoCloseRedisLocker.lock();
        if (!lock){
              throw new IllegalStateException("上锁失败");
        }
        return autoCloseRedisLocker;
    }

}
