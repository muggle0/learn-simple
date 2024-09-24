package com.muggle.jprotobuf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * Date 2024/9/18
 * Created by muggle
 */

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "";
    }
}
