package com.muggle.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/2$
 **/
@RestController
public class TestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    private   AdminClient adminClient;

//    @Scheduled(cron = "*/15 * * * * ?")
    public void send() {
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("xxxxx", "test");
    }

    //    @Scheduled(cron = "*/15 * * * * ?")
    public void send0() {
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("xxxxx", "test");
        send.addCallback(new ListenableFutureCallback(){
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    //    @Scheduled(cron = "*/15 * * * * ?")
    public void send1() {
        try {
            kafkaTemplate.send("xxxxx", "test").get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    //    @Scheduled(cron = "*/15 * * * * ?")
    public void sendDefault() {
        kafkaTemplate.sendDefault("xxx");
    }
    @KafkaListener(topics = "xxxxx",groupId = "test-consumer-group")
    public void listen(ConsumerRecord<?, String> record) throws ExecutionException, InterruptedException {
        String value = record.value();
        System.out.println(value);
    }


//    @GetMapping("/createTopic")
    public String createTopic(){
        Collection<NewTopic> newTopics = new ArrayList<>(1);
        newTopics.add(new NewTopic("topic-a",1,(short) 1));
        adminClient.createTopics(newTopics);
        System.out.println("》》》》》》》》》》》》》》》 创建topic");
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        System.out.println(">>>>>>>>>>>>>>>>>>>获取列表");
        return "success";
    }
//    https://blog.csdn.net/zzpdljd1991/article/details/90794156


    @GetMapping("/getTopic")
    public String getTopic() throws ExecutionException, InterruptedException {
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        Collection<TopicListing> topicListings = listTopicsResult.listings().get();
        System.out.println(">>>>>>>>>>>>>>>>>>>获取列表");
        return "success";
    }

    //@Transactional
//    @Scheduled(cron = "*/15 * * * * ?")
    public void sendTrans() {
      kafkaTemplate.executeInTransaction(t ->{
          t.send("xxxxx","test1");
          t.send("xxxxx","test2");
          return true;
      }
          );
    }


}
