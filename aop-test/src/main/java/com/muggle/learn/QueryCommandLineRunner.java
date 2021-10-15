package com.muggle.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/26
 **/
@Order(1)
//@Component
public class QueryCommandLineRunner /*implements CommandLineRunner*/ {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    DefaultListableBeanFactory beanFactory;
    @PostConstruct
    public void test() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestInterface.class);
        enhancer.setCallback(new MyMethodInterceptor());
        TestInterface test = (TestInterface) enhancer.create();
        beanFactory.registerSingleton("testInterface",test);
    }
}
