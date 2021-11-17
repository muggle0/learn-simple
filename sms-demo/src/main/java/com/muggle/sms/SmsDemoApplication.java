package com.muggle.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsDemoApplication.class, args);
    }

}
