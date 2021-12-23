package com.muggle.sms.webservice;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

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
    @Resource
    private WebServiceContext webServiceContext;
    @Override
    public String sendMessage(String username) {
        HttpServletRequest request = (HttpServletRequest) webServiceContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        return "=====Hello! " + username + "=====";
    }

    @Override
    public boolean getFlag(String username) {
        return false;
    }
}
