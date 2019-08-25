package com.muggle.handler;


import com.muggle.entity.Message;
import com.muggle.entity.MyMessagePostProcessor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class ScheduleHandler {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Scheduled(fixedRate = 6000)
    private void simpleQueueSchedule() {
        System.out.println("<<<<<<<<<<");
        rabbitTemplate.convertAndSend("xx-simple-queue","ni----hao");
    }

    @Scheduled(fixedRate = 6000)
    private void objSimpleQueueSchedule() {
        System.out.println("<<<<<<<<<<");
        Message message = new Message();
        message.setTitle("hello");
        message.setContent("how are you ");
        rabbitTemplate.convertAndSend("obj-simple-queue",message);
    }

    @Scheduled(fixedRate = 1000)
    private void sendMessageForDlx() {
//        rabbitTemplate.
//        rabbitTemplate.convertAndSend("dlx-exchange","test-dlx","test");
        rabbitTemplate.convertAndSend("dlx-exchange","test-dlx","test",new MyMessagePostProcessor("1000"));
        /*
        * 消息确认
启动消费返回（@ReturnList注解，生产者就可以知道哪些消息没有发出去）
生产者和Server（broker）之间的消息确认
消费者和Server（broker）之间的消息确认
        * */

    }
}
