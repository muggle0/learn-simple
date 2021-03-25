package com.muggle.config;

import javax.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 同步的kafka需要ReplyingKafkaTemplate,指定repliesContainer
     *
     * @param producerFactory
     * @param repliesContainer
     * @return
     */
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
        ProducerFactory<String, String> producerFactory,
        ConcurrentMessageListenerContainer<String, String> repliesContainer) {
        ReplyingKafkaTemplate template = new ReplyingKafkaTemplate<>(producerFactory, repliesContainer);
        //同步相应超时时间：10s
        template.setReplyTimeout(10000);
        return template;
    }

    @Bean
    public ProducerFactory<String,String> producerFactory(KafkaProperties properties) {
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(properties.buildProducerProperties());
        producerFactory.setTransactionIdPrefix(properties.getProducer().getTransactionIdPrefix());
        return  producerFactory;
//        return new DefaultKafkaProducerFactory<>(properties.producerConfigs(properties));
    }

    public Map<String, Object> producerConfigs(KafkaProperties properties) {
        Map<String, Object> props = new HashMap<>();
        //用于建立与kafka集群的连接，这个list仅仅影响用于初始化的hosts，来发现全部的servers。 格式：host1:port1,host2:port2,…，
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,String.join(",",properties.getBootstrapServers()));
        // 重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        // Producer可以将发往同一个Partition的数据做成一个Produce Request发送请求以减少请求次数，该值即为每次批处理的大小,若将该值设为0，则不会进行批处理
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // Producer可以用来缓存数据的内存大小。该值实际为RecordAccumulator类中的BufferPool，即Producer所管理的最大内存。
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        //发送一次message最大大小，默认是1M
        //props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 20971520);
        // 序列化器
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     * 指定consumer返回数据到指定的topic
     * @return
     */
    @Bean
    public ConcurrentMessageListenerContainer<String, String>
    repliesContainer(ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
            containerFactory.createContainer("topic-return");
        repliesContainer.setAutoStartup(true);
        return repliesContainer;
    }

    @Bean
//    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate<?, ?> kafkaTemplate(ProducerFactory<String, String> kafkaProducerFactory,
                                             ObjectProvider<RecordMessageConverter> messageConverter,KafkaProperties properties) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        messageConverter.ifUnique(kafkaTemplate::setMessageConverter);
        kafkaTemplate.setProducerListener( new LoggingProducerListener<>());
        kafkaTemplate.setDefaultTopic(properties.getTemplate().getDefaultTopic());
        return kafkaTemplate;
    }


}
