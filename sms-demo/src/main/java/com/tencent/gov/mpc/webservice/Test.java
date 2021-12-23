package com.tencent.gov.mpc.webservice;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import com.muggle.sms.webservice.ClientUtils;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class Test {
    public static void main(String[] args) throws Exception {
        test4();
    }



    public void test1(){
        String url = "http://192.168.0.101:8089/myservice?wsdl";
        Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(url));
            // WSDL里面描述的接口名称(要调用的方法)
            call.setOperationName(new QName("http://com.soft.ws/my",
                    "authorization"));
            //跨平台调用加上这个
            call.setUseSOAPAction(true);
            call.setSOAPActionURI("http://com.soft.ws/my/authorization");
            // 接口方法的参数名, 参数类型,参数模式 IN(输入), OUT(输出) or INOUT(输入输出)
            call.addParameter("userId", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("password", XMLType.XSD_STRING, ParameterMode.IN);
            // 设置被调用方法的返回值类型
            call.setReturnType(XMLType.XSD_STRING);
            // 设置方法中参数的值
            Object result = call.invoke(new Object[] { "admin", "123456" });

            System.out.println(result.toString());
        } catch (ServiceException | RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void test2(){
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setServiceClass(MyService.class);
        factory.setAddress("http://192.168.0.101:8089/myservice?wsdl");
        // 需要服务接口文件
//        MyService client = (MyService) factory.create();
//        String result = client.authorization("admin", "123456");
//        System.out.println(result);
    }

    public static void test3() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf
                .createClient("http://10.21.233.179/services/Sms?wsdl");
        QName qName = new QName("http://com.tencent.mpc", "ConnMas");
        Object[] result = client.invoke(qName,
                new Object[] { "admin", "123456" });
        System.out.println(result[0]);
    }

    public static void test4() throws RemoteException, MalformedURLException {
        SmsServiceLocator locator = new SmsServiceLocator();
        SmsSoapBindingStub stub = new SmsSoapBindingStub(new java.net.URL(locator.getSmsAddress()),locator);
        final String username = ClientUtils.encrypt("AjkDbl0+UjA=", "chinagdn");
        final String password = ClientUtils.encrypt("Al0DJl12UmhXI1AyU1FXZ1w/B2MFNw==","chinagdn" );

        String string = stub.connMas(username, password);
        System.out.println(string);
        Resp userTest = (Resp) XMLUtil.convertXmlStrToObject(Resp.class, string);
    }




    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "head")
    @XmlType(propOrder = {
            "message",
            "code"
    })
    public static class Resp implements Serializable {
        public Resp() {
        }

        public Resp(String code, String message) {
            this.code = code;
            this.message = message;
        }

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private static void test5() {
        Resp resp = new Resp();
        resp.setCode("xxx");
        resp.setMessage("xxx");
        XMLUtil.convertToXml(resp);

    }
    /*

<?xml version="1.0" encoding="GBK" ?>
<response>
<head>
<code>2</code>
  <message>用户名密码错误</message>
</head>
</response>

    * */
}
