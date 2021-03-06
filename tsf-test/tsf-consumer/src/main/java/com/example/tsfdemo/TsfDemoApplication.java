package com.example.tsfdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.tsf.annotation.EnableTsf;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"com.example.tsfdemo.feign"})
@EnableTsf
public class TsfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsfDemoApplication.class, args);
    }

}
