package com.bear.server.netty;

import com.bear.common.dto.BaseProto;
import com.bear.common.dto.UserProto;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wuyd
 * 2021/2/25 11:23
 */
@Slf4j
@ChannelHandler.Sharable
@Service("nettyServerHandler")
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 建立连接时，发送一条消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        log.info("连接的客户端地址:" + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    /**
     * 超时处理 如果5秒没有接受客户端的心跳，就触发; 如果超过两次，则直接关闭;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj){
        log.info("userEventTriggered");
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {
        BaseProto.Base base = (BaseProto.Base) msg;
        UserProto.User user = UserProto.User.parseFrom(base.getBody().getBodyBytes());
        log.info("channelRead :" + base.toString());
        log.info("channelRead" + user);
        ctx.writeAndFlush(base);
    }


    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
