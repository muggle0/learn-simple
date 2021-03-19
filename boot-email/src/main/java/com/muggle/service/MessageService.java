package com.muggle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    JavaMailSender mailSender;

//    @Scheduled(cron = "*/10 * * * * ?")
    public void test(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("1977339740@qq.com");//发送者
        msg.setTo("\n" +
                "1977339740@qq.com");//接收者
        msg.setSubject("邮箱标题");//标题
        msg.setText("xxxxxxxx");//内容
        mailSender.send(msg);
    }

//    @Scheduled(cron = "*/10 * * * * ?")
    public void test1() throws MessagingException {
        String content = "";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("1977339740@qq.com");//发送者
        helper.setTo("1977339740@qq.com");//接收者
        helper.setSubject("邮箱标题");//标题
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void test3() throws MessagingException {
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);

        //设置发送者
        mimeMessageHelper.setFrom("1977339740@qq.com");

        //设置接收者
        mimeMessageHelper.setTo("1977339740@qq.com");

        //设置主题（标题）
        mimeMessageHelper.setSubject("你好");
        //设置正文（可带有页面标签）
        mimeMessageHelper.setText("<pre class=\"ql-syntax\" spellcheck=\"false\"><table border=\"1\" style=\"border-collapse: collapse;\">\n" +
                "  <tr>\n" +
                "    <th>list1</th>\n" +
                "    <th>list4</th>\n" +
                "    <th>list3</th>\n" +
                "    <th>list2</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>>>>></td>\n" +
                "    <td>>>>></td>\n" +
                "    <td>>>>></td>\n" +
                "    <td>>>>></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td><<<<</td>\n" +
                "    <td><<<<</td>\n" +
                "    <td><<<<</td>\n" +
                "    <td><<<<</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>>>>></td>\n" +
                "    <td>>>>></td>\n" +
                "    <td>>>>></td>\n" +
                "    <td>>>>></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "</pre><p><br></p>", true);

        mailSender.send(mimeMailMessage);
    }
}
