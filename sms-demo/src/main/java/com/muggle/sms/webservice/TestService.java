package com.muggle.sms.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Description
 * Date 2021/11/11
 * Created by muggle
 */
/*@WebService(name = "TestService", // 暴露服务名称
    targetNamespace = "http://server.webservice.Bag.admin.com"// 命名空间,一般是接口的包名倒序
)*/
public interface TestService {
    @WebMethod
    public String sendMessage(@WebParam(name = "username") String username);

    @WebMethod
    public boolean getFlag(@WebParam(name = "username") String username);

}
