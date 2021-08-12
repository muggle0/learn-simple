package com.muggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
* @author muggle
* @Description
* @createTime 2020-12-18
*/

@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableScheduling
@Configuration
public class GeneratorTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorTestApplication.class, args);
    }
}
