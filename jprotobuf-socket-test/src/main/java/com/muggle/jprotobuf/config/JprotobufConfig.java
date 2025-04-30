package com.muggle.jprotobuf.config;

import com.baidu.jprotobuf.pbrpc.spring.annotation.CommonAnnotationBeanPostProcessor;
import com.baidu.jprotobuf.pbrpc.spring.annotation.ProtobufRpcAnnotationResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description jprotobuf 相关注解处理器
 * Date 2024/8/7
 * Created by muggle
 */

@Configuration
public class JprotobufConfig {

    @Bean
    public CommonAnnotationBeanPostProcessor getCommonAnnotationBeanPostProcessor() {
        CommonAnnotationBeanPostProcessor cabp = new CommonAnnotationBeanPostProcessor();

        ProtobufRpcAnnotationResolver resolver = new ProtobufRpcAnnotationResolver();
        cabp.setCallback(resolver);

        return cabp;
    }
}
