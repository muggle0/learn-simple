package com.muggle.config;

import javax.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/9/21
 **/


@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic2() {
        return new NewTopic("topic-kl", 1, (short) 1);
    }

    @Bean
    public AdminClient init( KafkaProperties kafkaProperties){
        return KafkaAdminClient.create(kafkaProperties.buildAdminProperties());
    }
}
