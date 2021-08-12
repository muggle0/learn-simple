package com.muggle.websockettest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@SpringBootApplication
@ConditionalOnBean
public class WebsocketTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketTestApplication.class, args);
    }

}
