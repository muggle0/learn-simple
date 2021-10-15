package com.muggle.learn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description
 * Date 2021/10/1
 * Created by muggle
 */
@RestController
public class ProxyTestController {
    @GetMapping("/baiyifengyun/ajax/blogStats")
    public String test(HttpServletRequest request){
            return ">>>>>>>>>>>";
    }
}
