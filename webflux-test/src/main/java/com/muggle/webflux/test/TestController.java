package com.muggle.webflux.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/24
 **/
@RestController
public class TestController {
    @Autowired
    ReactiveWebServerApplicationContext context;

    @GetMapping("/")
    public Mono<String> test(){
        return Mono.just("..........");
    }

    @GetMapping("/socket")
    public Flux<String> longSocket(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(result -> ">>>");
    }
}
