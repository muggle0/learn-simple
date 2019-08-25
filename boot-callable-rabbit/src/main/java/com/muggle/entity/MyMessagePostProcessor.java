package com.muggle.entity;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;

public class MyMessagePostProcessor implements MessagePostProcessor {
    private String expirTime;

    public MyMessagePostProcessor(String expirTime){
        this.expirTime=expirTime;
    }
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
//        设置优先级
        message.getMessageProperties().setPriority(1);
//        设置过期时间
        message.getMessageProperties().setExpiration(expirTime);
//        设置消息持久化
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        return message;
    }

}
