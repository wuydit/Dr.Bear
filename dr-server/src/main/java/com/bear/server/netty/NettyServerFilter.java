package com.bear.server.netty;

import com.bear.common.dto.BaseProto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
    protected void initChannel(SocketChannel ch){
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(BaseProto.Base.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        //业务逻辑实现类
        pipeline.addLast("nettyServerHandler", nettyServerHandler);
    }
}
