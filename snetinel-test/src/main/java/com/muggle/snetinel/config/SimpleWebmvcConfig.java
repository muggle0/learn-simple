package com.muggle.snetinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description
 * Date 2021/5/5
 * Created by muggle
 */
public class SimpleWebmvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注冊sentinel 拦截器
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        config.setHttpMethodSpecify(true);
        config.setWebContextUnify(true);
        config.setBlockExceptionHandler(new SimpleBlockExceptionHandler());
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
    }
}
