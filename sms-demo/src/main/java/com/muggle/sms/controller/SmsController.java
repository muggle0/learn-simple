package com.muggle.sms.controller;

import com.alibaba.fastjson.JSONObject;
import com.muggle.sms.entity.MessageTunnelEntity;
import com.muggle.sms.repository.MessageTunnelRepository;
import com.muggle.sms.webservice.ClientUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.namespace.QName;
import java.util.Arrays;

/**
 * Description
 * Date 2021/10/14
 * Created by muggle
 */
@RestController
public class SmsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsController.class);
    @Autowired
    private MessageTunnelRepository tunnelRepository;
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/test")
    public String test(){
        final MessageTunnelEntity byTunnelId = tunnelRepository.findByTunnelId(1l);
        MessageTunnelEntity messageTunnelEntity = new MessageTunnelEntity();
        messageTunnelEntity.setMessageChannel((byte) 0);
        messageTunnelEntity.setTunnelStatus((byte) 1);
        messageTunnelEntity.setDeleted((byte) 1);
        Example<MessageTunnelEntity> example = Example.of(messageTunnelEntity);
        Sort sort = Sort.by(Sort.Direction.ASC, "tunnelId");
        Pageable pageable = PageRequest.of(1, 2, sort);
        Page<MessageTunnelEntity> tunnelPage = tunnelRepository.findAll(example, pageable);
        LOGGER.info(">>>{}", JSONObject.toJSONString(tunnelPage));
        return "xxx";
    }

    @GetMapping("/region")
    public String test2(){
//        username=AjkDbl0+UjA=，password=Al0DJl12UmhXI1AyU1FXZ1w/B2MFNw==  https://www.cnblogs.com/myitnews/p/12370308.html
        final String username = ClientUtils.encrypt("username=AjkDbl0+UjA=", "chinagdn");
        final String password = ClientUtils.encrypt("password=Al0DJl12UmhXI1AyU1FXZ1w/B2MFNw==","chinagdn" );
        try {
            final Object[] connMas = ClientUtils.callWeb("http://10.21.233.179/services/Sms?wsdl","ConnMas", username, password);
            LOGGER.info("connMas:{}", Arrays.toString(connMas));
            return "true";
        }catch (Exception e){
            LOGGER.error("调用异常",e);
            return "false";
        }
    }

    @GetMapping("/auth2")
    public String test3(){
        // 测试华为云  http://10.21.242.252:8089/services/Sms?wsdl
        final String url = "http://10.21.233.179".concat("/idp/oauth2/getToken").concat("?client_id=")
            .concat("tyxxpt").concat("&grant_type=authorization_code&code=").concat("xxx")
            .concat("&client_secret=").concat("02f702fdb3ef45e8929fdf66fb7c66be");
        final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
       LOGGER.info(exchange.getStatusCode().toString());
       LOGGER.info(exchange.getBody());
        // {"CLIENT_SECRET":"02f702fdb3ef45e8929fdf66fb7c66be"}  应用ID：tyxxpt
        return "xxx";
    }

    public static void main(String[] args) throws Exception {
        final String username = ClientUtils.encrypt("AjkDbl0+UjA=", "chinagdn");
        final String password = ClientUtils.encrypt("Al0DJl12UmhXI1AyU1FXZ1w/B2MFNw==","chinagdn" );
        ClientUtils.callWeb("http://10.21.233.179/services/Sms?wsdl","ConnMas", username, password);
//        sendWsdl(null);
    }



    public static String sendWsdl(Object obj) {
        System.out.println("--------调用webservice接口begin-------");
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        //对方的wsdl地址
        Client client = dcf.createClient("file:D:/workspace/java/github/learn-simple/sms-demo/src/main/java/com/muggle/sms/controller/Sms.wsdl");
        String json = null;
        try {

            QName qName = new QName("http://xx.zygxsq.cn/", "ConnMas");
            final String username = ClientUtils.encrypt("AjkDbl0+UjA=", "chinagdn");
            final String password = ClientUtils.encrypt("Al0DJl12UmhXI1AyU1FXZ1w/B2MFNw==","chinagdn" );//*原文章链接：https://blog.csdn.net/qq_27471405/article/details/105275657     * 其他均为盗版，公众号：灵儿的笔记(zygxsq)
            Object[] objects1= client.invoke(qName, username,password); //参数1，参数2，参数3......按顺序放就看可以

            json = JSONObject.toJSONString(objects1[0]);
            System.out.println("返回数据:" + json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器断开连接，请稍后再试");
        }
        System.out.println("--------调用webservice接口end-------");
        return json;


    }

}
