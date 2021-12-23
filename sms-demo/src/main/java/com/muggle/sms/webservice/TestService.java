package com.muggle.sms.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Description
 * Date 2021/11/11
 * Created by muggle
 */
@WebService(name = "Sms", // 暴露服务名称
    targetNamespace = "http://10.21.242.252:8089/services/Sms"// 命名空间,一般是接口的包名倒序
)
public interface TestService {
    @WebMethod
    String sendMessage(@WebParam(name = "username") String username);

    @WebMethod
    boolean getFlag(@WebParam(name = "username") String username);


}
