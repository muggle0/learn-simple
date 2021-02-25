package com.muggle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    JavaMailSender mailSender;

    @Scheduled(cron = "*/10 * * * * ?")
    public void test(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("1977339740@qq.com");//发送者
        msg.setTo("\n" +
                "1977339740@qq.com");//接收者
        msg.setSubject("邮箱标题");//标题
        msg.setText("xxxxxxxx");//内容
        mailSender.send(msg);
    }
}
