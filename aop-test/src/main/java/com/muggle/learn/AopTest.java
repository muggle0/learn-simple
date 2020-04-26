package com.muggle.learn;

import com.muggle.learn.annotation.TestQuery;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/26
 **/

@Aspect
@Component
public class AopTest {

    @Pointcut("@annotation(com.muggle.learn.annotation.TestQuery)")
    public void request() {
    }


    @Around("request()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        TestQuery annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(TestQuery.class);
        String processor = annotation.processor();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Type type = method.getAnnotatedReturnType().getType();
        return type.getTypeName();
    }

}
