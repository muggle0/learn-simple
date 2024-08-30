package com.muggle.learn;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description
 * Date 2024/8/30
 * Created by muggle
 */
@Component
public class MyTask {

    @Scheduled(cron = "0/5 * * * * ?")
    public String test(){
        System.out.println(">>>>>>>>>>>>");
        return ">>>>>";
    }
}
