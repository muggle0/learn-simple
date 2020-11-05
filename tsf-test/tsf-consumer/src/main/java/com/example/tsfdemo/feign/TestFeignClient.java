package com.example.tsfdemo.feign;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/9/30
 **/

@FeignClient("tsf-provide")
@Repository
public interface TestFeignClient {

    @GetMapping("/test")
    String test();

    public static void main(String[] args) {
        String test="'202007010 000000";
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        LocalDateTime parse = LocalDateTime.parse(test, yyyyMMdd);
        System.out.println(parse);
    }
}
