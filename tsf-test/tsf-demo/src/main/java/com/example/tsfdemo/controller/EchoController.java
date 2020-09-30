package com.example.tsfdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/9/30
 **/
@RestController
public class EchoController {


    @GetMapping("/test")
    public String test(){
        return ">>>>>>>>>>>";
    }
}
