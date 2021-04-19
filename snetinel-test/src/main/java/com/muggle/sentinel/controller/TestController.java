package com.muggle.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * Date 2021/4/19
 * Created by muggle
 */
@RestController
public class TestController {

    @GetMapping("/test")
    @SentinelResource(value = "test.hello",fallback = "helloError")
    public String test(){
        return "success";
    }

    @GetMapping("/test0")
    public String test0(){
        try {
            Entry resourceName = SphU.entry("test.hello");
            return resourceName.toString();
        } catch (BlockException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String helloError(String name,Throwable e){
        return  "xxx";
    }


}
