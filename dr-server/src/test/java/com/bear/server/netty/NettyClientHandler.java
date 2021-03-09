package com.bear.server.netty;

import com.bear.common.dto.ActionProto;
import com.bear.common.dto.BaseProto;
import com.bear.common.dto.UserProto;
import com.bear.server.config.SocketConfig;
import com.bear.server.control.tcp.BaseSocketControl;
import com.bear.server.utils.SpringUtils;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wuyd
 * 2021/2/25 11:23
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 建立连接时，发送一条消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        log.info("连接的客户端地址:" + ctx.channel().remoteAddress());
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    /**
     * 超时处理 如果5秒没有接受客户端的心跳，就触发; 如果超过两次，则直接关闭;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        log.info("userEventTriggered");
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseProto.Base base = (BaseProto.Base) msg;
        if(base.getHead().getAction() == ActionProto.Action.getUserInfo_VALUE){
            UserProto.UserResult result = UserProto.UserResult.parseFrom(base.getBody().getBodyBytes());
            log.info(result.toString());
        }
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught");
        ctx.close();
    }
}
