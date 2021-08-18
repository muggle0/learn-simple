package com.muggle.websockettest.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Description
 * Date 2021/8/17
 * Created by muggle
 */
public class MyWebSocketHandler extends TextWebSocketHandler {

    public void handleTextMessage(WebSocketSession session, TextMessage message)
        throws IOException {
        session.sendMessage(new TextMessage(String.format("收到用户：【%s】发来的【%s】",
            session.getAttributes().get("token"), message.getPayload())));
    }

}
