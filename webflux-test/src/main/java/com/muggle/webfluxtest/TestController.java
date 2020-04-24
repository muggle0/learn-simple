package com.muggle.webfluxtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/24
 **/
@RestController
public class TestController {
    @Autowired
    WebApplicationContext context;

    @GetMapping("/")
    public Mono<String> test(){
        return Mono.just("..........");
    }
}
