package com.muggle.sms.webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * Description
 * Date 2021/11/11
 * Created by muggle
 */
public class clientUtil {

    public static String callWebSV(String wsdUrl, String operationName, String... params) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdUrl);
        client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects;
        // invoke("方法名",参数1,参数2,参数3....);
        objects = client.invoke(operationName, params);
        return objects[0].toString();
    }
}
