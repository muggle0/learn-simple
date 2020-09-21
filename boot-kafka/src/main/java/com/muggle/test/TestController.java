package com.muggle.test;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
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
    @Autowired
    AdminClient client;

//    @Scheduled(cron = "*/15 * * * * ?")
    public void send() {
        kafkaTemplate.send("xxxxx", "test");
    }

//    @KafkaListener(topics = "xxxxx",groupId = "test-consumer-group")
    public void listen(ConsumerRecord<?, String> record) {
        String value = record.value();
        System.out.println(value);
    }

    @Scheduled(cron = "*/15 * * * * ?")
    public void getTopic(){
        Collection<NewTopic> newTopics = new ArrayList<>(1);
        newTopics.add(new NewTopic("topic-kl",1,(short) 1));
        client.createTopics(newTopics);
    }

//    https://blog.csdn.net/zzpdljd1991/article/details/90794156

}
