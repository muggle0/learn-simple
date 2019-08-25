package com.muggle.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RabbitTestService {
    @Autowired
    RabbitTemplate template;

    @Transactional(rollbackFor = Exception.class)
    public void test() throws InterruptedException {
        for (int i = 0; i < 30; i++) {

            template.convertAndSend("test for " + i);
            System.out.println(">>>>>" +i);
        }
        Thread.sleep(1000);
        throw new RuntimeException();


    }
}
