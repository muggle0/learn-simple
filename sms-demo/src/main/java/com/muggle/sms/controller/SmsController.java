package com.muggle.sms.controller;

import com.muggle.sms.entity.MessageTunnelEntity;
import com.muggle.sms.repository.MessageTunnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * Date 2021/10/14
 * Created by muggle
 */
@RestController
public class SmsController {
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
        return "xxx";
    }
}
