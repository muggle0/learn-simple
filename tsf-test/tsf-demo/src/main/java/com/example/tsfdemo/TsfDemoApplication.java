package com.example.tsfdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TsfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsfDemoApplication.class, args);
    }

}
