package com.muggle.http.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServertInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
         ChannelPipeline pipeline = socketChannel.pipeline();
//        对web响应编解码
        pipeline.addLast("httpserverCodec",new HttpServerCodec())
//        起名加入管道  自己的处理器
                .addLast("testHttpResponse",new HttpServerHandler())
                .addLast(new HttpObjectAggregator(65536));

    }
}
