package com.muggle.learn;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/26
 **/
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return "test";
    }

    public static void main(String[] args) {
        String sbeTenantId="1,2,3";
        String tenantId="2";
        if(StringUtils.isNotBlank(sbeTenantId)){
            String [] split = sbeTenantId.split(",");
            boolean tenantFlag=false;
            for(String str:split){
                if(Objects.equals(tenantId, str)){
                    tenantFlag=true;
                    break;
                }
            }
            System.out.println(tenantFlag);
        }
        System.out.println("xxxxxxxxxxxxxxx");
    }
}
