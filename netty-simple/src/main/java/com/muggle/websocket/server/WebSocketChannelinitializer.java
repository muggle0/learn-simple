package com.muggle.websocket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelinitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        链接通道
        ChannelPipeline channelPipeline=socketChannel.pipeline();
//        http编解码器
        channelPipeline.addLast(new HttpServerCodec());
//        块写入
        channelPipeline.addLast(new ChunkedWriteHandler());
//                                    使用很多,netty 将请求切分成多段处理，这个东西将它聚合
        channelPipeline.addLast(new HttpObjectAggregator(8192));
//        websocket处理器 帧 frames 传递帧 六种 二进制 关闭 断点续传 ping pong 文本
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
    }
}
