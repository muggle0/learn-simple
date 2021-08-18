package com.muggle.websockettest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class WebsocketTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketTestApplication.class, args);
    }

}
