package com.muggle.sms.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * Description
 * Date 2021/11/11
 * Created by muggle
 */

@WebService(serviceName = "TestService", // 与接口中指定的name一致
    targetNamespace = "http://server.webservice.Bag.admin.com", // 与接口中的命名空间一致,一般是接口的包名倒
    endpointInterface = "com.muggle.sms.webservice.TestService"// 接口地址
)
@Component
public class TestServiceImpl implements TestService {
    @Override
    public String sendMessage(String username) {

        return "=====Hello! " + username + "=====";
    }

    @Override
    public boolean getFlag(String username) {
        return false;
    }
}
