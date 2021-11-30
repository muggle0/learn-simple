package com.muggle.sms.controller;

import com.alibaba.fastjson.JSONObject;
import com.muggle.sms.entity.MessageTunnelEntity;
import com.muggle.sms.repository.MessageTunnelRepository;
import com.muggle.sms.webservice.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        username=AjkDbl0+UjA=，password=Al0DJl12UmhXI1AyU1FXZ1w/B2MFNw==
        final String username = ClientUtils.encrypt("username=AjkDbl0+UjA=", "chinagdn");
        final String password = ClientUtils.encrypt("password=Al0DJl12UmhXI1AyU1FXZ1w/B2MFNw==","chinagdn" );
        try {
            final Object[] connMas = ClientUtils.callWeb("10.21.242.252:8089","ConnMas", username, password);
            LOGGER.info("connMas:{}", Arrays.toString(connMas));
            return "true";
        }catch (Exception e){
            LOGGER.error("调用异常",e);
            return "false";
        }
    }

}
