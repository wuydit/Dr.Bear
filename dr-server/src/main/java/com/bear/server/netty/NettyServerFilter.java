package com.bear.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * @author wuyd
 * 2021/2/25 11:22
 */
@Component
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    private final NettyServerHandler nettyServerHandler;

    public NettyServerFilter(NettyServerHandler nettyServerHandler) {
        this.nettyServerHandler = nettyServerHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 解码和编码，应和客户端一致
        //传输的协议 Protobuf
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast("timeout", new IdleStateHandler(60, 0, 0));
        //业务逻辑实现类
        pipeline.addLast("nettyServerHandler", nettyServerHandler);
    }
}
