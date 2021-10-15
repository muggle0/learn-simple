package com.muggle.learn.annotation;

import com.muggle.learn.TestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/26
 **/
//@Component
@Order(10)
public class TestSchdule {
    @Autowired
    TestInterface testInterface;

    @Scheduled(cron = "0/5 * * * * ?")
    public void test(){
        testInterface.test();
    }
}
