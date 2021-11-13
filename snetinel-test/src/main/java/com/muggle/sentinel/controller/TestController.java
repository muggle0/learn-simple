package com.muggle.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
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
    @SentinelResource(value = "test.hello",fallback = "testFallback")
    public String test(){
        return "success";
    }

    @GetMapping("/test0")
    public String test0(){
        try {
            Entry resourceName = SphU.entry("test.hello");
            return resourceName.getCreateTimestamp() + "";
        } catch (BlockException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String testFallback() {
        return "xxx";
    }

    @GetMapping("/test1")
    public String test1() {
        return ">>>>>>>>";
    }


    @GetMapping("/test2")
    @SentinelResource(value = "test.hello")
    public String test2(){
        return ">>>>>>>>";
    }

    @GetMapping("/test3")
    @SentinelResource(entryType = EntryType.IN)
    public String test3(){
        return ">>>>>>>>";
    }

    public static void main(String[] args) {

    }
}
