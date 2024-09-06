package com.muggle.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Description
 * Date 2023/12/7
 * Created by muggle
 */
@RestController
@RequestMapping("/home")
public class TestController {

    private RestTemplate restTemplate= new RestTemplate();

    @GetMapping("/page")
    public String homePage(){
        return ">>";
    }

}
