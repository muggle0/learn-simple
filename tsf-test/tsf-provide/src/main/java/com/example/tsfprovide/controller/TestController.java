package com.example.tsfprovide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/9/30
 **/

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return ">>>>>>>>>>>>>>>";
    }
}
