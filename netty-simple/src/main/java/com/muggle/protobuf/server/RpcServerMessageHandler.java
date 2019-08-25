package com.muggle.protobuf.server;

import com.muggle.protobuf.TestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcServerMessageHandler extends SimpleChannelInboundHandler<TestMessage.Book> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TestMessage.Book book) throws Exception {
        System.out.println(book);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>>>>>>>>>>");
    }
}
