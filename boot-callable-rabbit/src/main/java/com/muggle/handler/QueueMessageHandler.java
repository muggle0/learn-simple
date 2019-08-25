package com.muggle.handler;

import com.muggle.entity.Message;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QueueMessageHandler {



    @RabbitListener(queues = { "obj-simple-queue"})
    public void testCallBack(Message msg,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag){
//        try {
//            // 做些啥
//            if (xxx){
//                channel.basicAck(tag,false);
//            }else {
//                channel.basicNack(tag,false,true);
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(msg);
    }

    @RabbitListener(queues = { "gc-queue"})
    public void gcMessage(String message){
        System.out.println(message);
    }

}
