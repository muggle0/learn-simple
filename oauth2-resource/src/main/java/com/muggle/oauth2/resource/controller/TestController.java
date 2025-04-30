package com.muggle.oauth2.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * Date 2023/12/19
 * Created by muggle
 */

@RestController
@RequestMapping("/a")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "";
    }
}
