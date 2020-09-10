package com.muggle.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/2$
 **/
@Component
public class TestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "*/15 * * * * ?")
    public void send() {
        kafkaTemplate.send("zhisheng", "test");
    }

    @KafkaListener(topics = "zhisheng",groupId = "test-consumer-group")
    public void listen(ConsumerRecord<?, ?> record) {
        Object value = record.value();
        System.out.println(value);
    }

}
