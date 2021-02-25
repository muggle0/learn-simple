package com.muggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootEmailApplication.class, args);
    }

}
