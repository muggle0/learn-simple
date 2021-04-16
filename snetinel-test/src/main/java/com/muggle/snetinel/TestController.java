package com.muggle.snetinel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * Date 2021/4/11
 * Created by muggle
 */

@RestController
public class TestController {

    @PostMapping("/test")
    public String test0(){
        return "test1>>>>>>>>>>>>>>>>>";
    }
}
