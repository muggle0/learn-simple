package com.muggle.http.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.logging.Logger;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private static Logger log=Logger.getLogger("ClientInitializer.class");

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("初始化处理器");
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("http-decoder",new HttpResponseDecoder())
                .addLast(new HttpObjectAggregator(65536))
                .addLast("http-encoder",new HttpRequestEncoder())
                .addLast("myHandler",new HttpClientHandler());
    }
}
