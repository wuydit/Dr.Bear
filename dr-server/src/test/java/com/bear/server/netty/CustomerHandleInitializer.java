package com.bear.server.netty;

import com.bear.common.dto.BaseProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author wuyd
 * 2021/3/8 14:33
 */
public class CustomerHandleInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel){
        channel.pipeline()
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(BaseProto.Base.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new NettyClientHandler());
    }
}
