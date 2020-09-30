package com.example.tsfdemo.feign;

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
}
