package com.muggle.config;

import com.muggle.callable.MyConfirmCallback;
import com.muggle.callable.MyReturnCallback;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RabbitConfig {
    @Autowired
    public RabbitConfig( RabbitTemplate rabbitTemplate,MyConfirmCallback confirmCallback,MyReturnCallback returnCallback){
//        rabbitTemplate.setReturnCallback(returnCallback);
//        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setChannelTransacted(true);
    }

    /*@Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("consumer_queue");              // 监听的队列
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);        // 手动确认
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {      //消息处理
            System.out.println("====接收到消息=====");
            System.out.println(new String(message.getBody()));
            if (message.getMessageProperties().getHeaders().get("error") == null) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("消息已经确认");
            } else {
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("消息拒绝");
            }

        });
        container.setPrefetchCount();
        return container;
    }*/


}
