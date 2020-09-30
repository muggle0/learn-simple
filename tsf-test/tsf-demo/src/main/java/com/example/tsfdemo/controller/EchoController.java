package com.example.tsfdemo.controller;

import com.example.tsfdemo.feign.TestFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/9/30
 **/
@RestController
public class EchoController {
    @Autowired
    TestFeignClient feignClient;


    @GetMapping("/test")
    public String test(){

        return feignClient.test();
    }
}
