package com.muggle.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * Date 2023/12/7
 * Created by muggle
 */
@RestController
@RequestMapping("/home")
public class TestController {

    @GetMapping("/page")
    public String homePage(){
        return ">>";
    }
}
