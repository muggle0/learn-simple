package com.muggle.http;

import com.muggle.http.client.ClientInitializer;
import com.muggle.http.server.ServertInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpTest {
    private static Logger log=Logger.getLogger("netty-simple-test");

    @Before
    public void init(){
        System.setProperty("java.util.logging.config.file","logging.properties");

    }
    @Test
    public void client(){
        log.info("启动http-netty客户端");
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());
            ChannelFuture localhost = bootstrap.connect("127.0.0.1", 8081).sync();
            Channel channel = localhost.channel();
            DefaultHttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "nihao");
            request.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html");

            channel.writeAndFlush(request);
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            e.printStackTrace();
        }finally {
            clientGroup.shutdownGracefully();
        }

    }

    @Test
    public void server(){

        log.info("启动http-netty服务端");
        //        事件循环组 接收连接，将连接发送给work
        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        work 干活
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
//      简化服务端启动
        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
//                     打开通道                                                                                     子处理器 服务端初始化器
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new ServertInitializer());
            final ChannelFuture sync = serverBootstrap.bind(8081).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.severe(e.getLocalizedMessage());
        } finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
