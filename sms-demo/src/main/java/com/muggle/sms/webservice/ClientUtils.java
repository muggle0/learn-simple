package com.muggle.sms.webservice;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;


/**
 * Description
 * Date 2021/11/11
 * Created by muggle
 */
public class ClientUtils {

    private static ClientLoginInterceptor loginInterceptor;

    // 初始化登录拦截器
    public static void initInterceptor(String username,String password){
       if  (loginInterceptor==null){
           synchronized(ClientUtils.class){
               if (loginInterceptor==null){
                   loginInterceptor=new ClientLoginInterceptor(username,password);
               }
           }
       }
    }

    public static Object[] callWebWithAuth(String wsdUrl, String operationName, String... params) throws Exception {
        if (loginInterceptor==null){
            throw new IllegalStateException("webservice 登录拦截器未初始化");
        }
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdUrl);
        client.getOutInterceptors().add(loginInterceptor);
        Object[] objects;
        objects = client.invoke(operationName, params);
        return objects;
    }

    public static Object[] callWeb(String wsdUrl, String operationName, String... params) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdUrl);
        Object[] objects;
        objects = client.invoke(operationName, params);
        return objects;

    }
    public static String encrypt(String txt, String key) {
        String encrypt_key = "0f9cfb7a9acced8a4167ea8006ccd098";
        int ctr = 0;
        String tmp = "";
        int i;
        for (i = 0; i < txt.length(); i++) {
            ctr = (ctr == encrypt_key.length()) ? 0 : ctr;
            tmp = tmp + encrypt_key.charAt(ctr)
                + (char) (txt.charAt(i) ^ encrypt_key.charAt(ctr));
            ctr++;
        }

        return base64_encode(key(tmp, key));
    }
    public static String key(String txt, String encrypt_key) {
        encrypt_key = strMD5(encrypt_key);
        int ctr = 0;
        String tmp = "";
        for (int i = 0; i < txt.length(); i++) {
            ctr = (ctr == encrypt_key.length()) ? 0 : ctr;
            int c = txt.charAt(i) ^ encrypt_key.charAt(ctr);
            String x = "" + (char) c;
            tmp = tmp + x;
            ctr++;
        }
        return tmp;
    }


    public  static String base64_encode(String str) {
        return new sun.misc.BASE64Encoder().encode(str.getBytes());
    }

    public static String base64_decode(String str) {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        if (str == null)
            return null;
        try {
            return new String(decoder.decodeBuffer(str));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String strMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static class ClientLoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
        private String username;
        private String password;
        public ClientLoginInterceptor(String username, String password) {
            super(Phase.PREPARE_SEND);
            this.username = username;
            this.password = password;
        }
        @Override
        public void handleMessage(SoapMessage soapMessage) throws Fault {
            List<Header> headers = soapMessage.getHeaders();
            Document doc = DOMUtils.createDocument();
            // todo
            Element auth = doc.createElement("authrity");
            Element username = doc.createElement("username");
            Element password = doc.createElement("password");
            username.setTextContent(this.username);
            password.setTextContent(this.password);
            auth.appendChild(username);
            auth.appendChild(password);
            headers.add(0, new Header(new QName("tiamaes"),auth));
        }
    }
}
