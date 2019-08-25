package com.muggle.http.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.logging.Logger;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static Logger log=Logger.getLogger("ClientInitializer.class");

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        log.info("读取客户端消息");
        httpObject.toString();
}

    @Override
    public void channelRegistered(ChannelHandlerContext context) throws Exception {
       System.out.println(">>>>>>");

    }

}
