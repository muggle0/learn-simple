package com.muggle.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MybatisPluginTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPluginTestApplication.class, args);
    }

}
