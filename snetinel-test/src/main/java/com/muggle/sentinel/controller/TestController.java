package com.muggle.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * Date 2021/4/19
 * Created by muggle
 */
@RestController
public class TestController {

    @GetMapping("/test")
    @SentinelResource(value = "test.hello",fallback = "helloError")
    public String test(){
        return "success";
    }

    public String helloError(String name,Throwable e){
        return  "xxx";
    }


}
