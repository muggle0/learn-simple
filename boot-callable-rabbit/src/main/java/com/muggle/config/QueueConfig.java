package com.muggle.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class QueueConfig {

    @Bean
    public Queue getSimpleQueue(){
        return new Queue("simple-queue");
    }

    @Bean
    public Queue getObjSimpleQueue(){
        return new Queue("obj-simple-queue");
    }

    @Bean("gcQueue")
    public Queue gcQueue(){
        Queue build = QueueBuilder.durable("gc-queue").build();
        return build;
    }

    @Bean("dlxQueue")
    public Queue dlxQueue(){
//        new Queue("text",true, false, false,new HashMap<>())
//        x-dead-letter-exchange声明了队列里的死信转发到的交换机名称
        Queue build = QueueBuilder.durable("dlx-queue").withArgument("x-dead-letter-exchange", "gc-exchange")
//                dead letter携带的routing-key
                .withArgument("x-dead-letter-routing-key", "dlx-key")
//                设置队列的优先级
                .withArgument("x-max-priority",10)
//                消息在过期的时间
                .withArgument("x-message-ttl",5000L).build();

//
      return build;
    }

    @Bean("dlxExchange")
    public DirectExchange  dlxExchange(){
//        ExchangeBuilder.topicExchange().durable()
        return new DirectExchange("dlx-exchange");
    }

    @Bean("gcExchange")
    public DirectExchange  gcExchange(){
        return new DirectExchange("gc-exchange");
    }

    @Bean
    public Binding bindingGcQueue(@Qualifier("gcQueue") Queue queue,@Qualifier("gcExchange")DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("dlx-key");
    }

    @Bean
    public Binding bindingDlxQueue(@Qualifier("dlxQueue") Queue queue,@Qualifier("dlxExchange")DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("test-dlx");
    }
}



