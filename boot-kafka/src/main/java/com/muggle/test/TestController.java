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
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @Autowired
    private  KafkaListenerEndpointRegistry listenerRegistry;

    @Autowired
    ReplyingKafkaTemplate<String, String, String> replyingTemplate;

    int i=1;

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

    @Transactional(rollbackFor = Exception.class)
//    @Scheduled(cron = "*/15 * * * * ?")
    public void sendFoo() {
        kafkaTemplate.send("topic_input", "test");

    }


    @KafkaListener( topics = "topic_input",autoStartup ="false" )
    public void listen(ConsumerRecord<?, String> record, Acknowledgment ack) {
        System.out.println("消费消息"+record.value());
        ack.acknowledge();
    }

    @KafkaListener( id = "listener1",topics = "test",autoStartup ="false" )
    public void testStart(ConsumerRecord<?, String> record){
        System.out.println(record.value());
    }

//    @Scheduled(cron = "*/15 * * * * ?")
    @Transactional
    public void testListener(){
        if (i==3){
            listenerRegistry.getListenerContainer("listener1").start();
        }
        System.out.println("生产者生产消息"+i++);
        kafkaTemplate.send("test","xxx"+i);
    }


    @KafkaListener(topics = "send-a")
    @SendTo("send-b")
    public String sendTest0(ConsumerRecord<?, String> record){
        System.out.println(record.value());
        return "转发消息"+record.value();
    }

    @KafkaListener(topics = "send-b")
    public void sendTest1(ConsumerRecord<?, String> record){
        System.out.println(record.value());
    }

//    @Scheduled(cron = "*/15 * * * * ?")
    @Transactional
    public void producerTest(){
        kafkaTemplate.send("send-a","xxxxxxxxxxxxxx");
    }

    @Scheduled(cron = "*/1 * * * * ?")
    @Transactional
    public void returnTestProducer(){
        ProducerRecord<String, String> record = new ProducerRecord<>("topic-return", "test-return");
        RequestReplyFuture<String, String, String> replyFuture = replyingTemplate.sendAndReceive(record);
        try {
            String value = replyFuture.get().value();
            System.out.println(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "topic-return")
    @SendTo
    public String listen(String message) {
        return "consumer return:".concat(message);
    }

}
