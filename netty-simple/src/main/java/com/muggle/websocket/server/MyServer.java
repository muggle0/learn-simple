package com.muggle.websocket.server;

import com.muggle.socket.server.MyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {
    public static void main(String[] args) {
        EventLoopGroup boss=new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
//
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker).channel(NioServerSocketChannel.class).childHandler(new MyServerInitializer());
            ChannelFuture sync = serverBootstrap.bind(8081).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
