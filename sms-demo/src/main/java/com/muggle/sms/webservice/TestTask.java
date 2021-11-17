package com.muggle.sms.webservice;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description
 * Date 2021/11/11
 * Created by muggle
 */
@Component
public class TestTask {
    //在一个方法中连续调用多次WebService接口，每次调用前需要重置上下文。
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @Scheduled(cron="0/4 * * * * ?")
    public void getMessage()  {
        Thread.currentThread().setContextClassLoader(classLoader);//在获取连接之前 还原上下文
        System.out.println("======开始调用webservice接口=====");
        String url = "http://localhost:8080/webService/TestService?wsdl";
        String methodName = "sendMessage";
        String result="";
        try {
            result=clientUtil.callWebSV(url, methodName, "name");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("接口调用失败！！！！");
        }
        System.out.println("===Finished!===恭喜你啊！！！CatService接口调用成功！！！===获得的数据是："+result);
    }

}
