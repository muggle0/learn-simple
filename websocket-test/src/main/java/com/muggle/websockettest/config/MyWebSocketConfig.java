package com.muggle.websockettest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Description
 * Date 2021/8/17
 * Created by muggle
 */

//@Configuration
//@EnableWebSocket
public class MyWebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry.addHandler(new MyWebSocketHandler(),"/**")
            //添加myHandler消息处理对象，和websocket访问地址
            //设置允许跨域访问
            .setAllowedOrigins("*")
            //添加拦截器可实现用户链接前进行权限校验等操作
            .addInterceptors(new MyHandshakeInterceptor());
    }


}
