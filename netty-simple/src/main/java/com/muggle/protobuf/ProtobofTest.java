package com.muggle.protobuf;

import com.muggle.protobuf.client.ClientInitializer;
import com.muggle.protobuf.server.RpcInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.util.logging.Logger;

public class ProtobofTest {
    private static Logger log=Logger.getLogger("ProtobofTest.class");

    @Test
    public void server(){
        log.info("启动Rpc客户端");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,work).channel(NioServerSocketChannel.class).childHandler(new RpcInitializer());
            ChannelFuture sync = serverBootstrap.bind(8081).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    @Test
    public void client(){
        log.info("启动Rpc服务端");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boss).channel(NioSocketChannel.class).handler(new ClientInitializer());
            ChannelFuture localhost = bootstrap.connect("127.0.0.1", 8081).sync();
            Channel channel = localhost.channel();
            channel.writeAndFlush(TestMessage.Book.newBuilder().setPage(10).setAuthor("ss").setName("ss").build());
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
        }
    }
}
