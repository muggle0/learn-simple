package com.example.demo.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Semaphore;

/**
 * Description
 * Date 2021/8/10
 * Created by muggle
 */
@RestController
public class TestLuaController {
    @Value("${test:false}")
    private Boolean test;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/lock")
    public String lock(){
        final AutoCloseRedisLocker test = new AutoCloseRedisLocker("test:testLock",
            "test", 200000L, redisTemplate);
        return String.valueOf(test.lock());
    }

    @GetMapping("/unLock")
    public String unlock()  {
        final AutoCloseRedisLocker test = new AutoCloseRedisLocker("test:testLock",
            "test", 200000L, redisTemplate);
        Semaphore semaphore = new Semaphore(2);

        try {
            test.close();
            return "true";
        }catch (Exception e){
            return "false";
        }

    }

    @GetMapping("/testTry")
    public String test(){
        try (final AutoCloseRedisLocker test = AutoCloseRedisLocker.buildAndLock("test:testLock","test",200000L,redisTemplate)){
            Thread.sleep(20000l);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

}
