package com.muggle.http.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    protected void channelRead0(ChannelHandlerContext context, HttpObject httpObject) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>>>>>");
       /* Channel channel = ctx.channel();
        channel.writeAndFlush("test");*/
    }


    @Override
    public void channelRegistered(ChannelHandlerContext context) throws Exception {
        System.out.println(">>>>>>");
//        super.channelRegistered(ctx);

    }


}
