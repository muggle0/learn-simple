package com.muggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootCallableRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootCallableRabbitApplication.class, args);
    }

}
