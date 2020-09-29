package com.muggle.test;

import javax.annotation.PostConstruct;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
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

//    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    KafkaProperties kafkaProperties;
    @Autowired
    private   AdminClient adminClient;
//    @PostConstruct
    public void init(){
         adminClient = KafkaAdminClient.create(kafkaProperties.buildAdminProperties());
    }


    @Scheduled(cron = "*/15 * * * * ?")
    public void send() {
        kafkaTemplate.send("xxxxx", "test");
    }

    @KafkaListener(topics = "xxxxx",groupId = "test-consumer-group")
    public void listen(ConsumerRecord<?, String> record) throws ExecutionException, InterruptedException {
        String value = record.value();
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        Collection<TopicListing> topicListings = listTopicsResult.listings().get();
        System.out.println(value);
    }

//    @Scheduled(cron = "*/15 * * * * ?")
//    public void getTopic(){
//        Collection<NewTopic> newTopics = new ArrayList<>(1);
//        newTopics.add(new NewTopic("topic-kl",1,(short) 1));
//        client.createTopics(newTopics);
//        System.out.println("》》》》》》》》》》》》》》》 创建topic");
//        ListTopicsResult listTopicsResult = client.listTopics();
//        System.out.println(">>>>>>>>>>>>>>>>>>>获取列表");
//    }
//    https://blog.csdn.net/zzpdljd1991/article/details/90794156

}
