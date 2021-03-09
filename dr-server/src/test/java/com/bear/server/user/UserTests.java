package com.bear.server.user;

import com.bear.common.dto.BaseProto;
import com.bear.common.dto.UserProto;
import com.bear.server.netty.CustomerHandleInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class UserTests {

	@Test
	void contextLoads() {
	}

	@Test
	void getUserInfo() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).handler(new CustomerHandleInitializer());
		ChannelFuture future = bootstrap.connect("127.0.0.1", 9000).sync();
		if(future.isSuccess()){
			log.info("启动成功");
		}
		SocketChannel socketChannel = (SocketChannel) future.channel();
		BaseProto.Head head = BaseProto.Head.newBuilder().setAction(10001).setToken("123").build();
		UserProto.User user = UserProto.User.newBuilder().setUid(223).build();
		BaseProto.Body body = BaseProto.Body.newBuilder().setBodyBytes(user.toByteString()).build();
		BaseProto.Base base = BaseProto.Base.newBuilder().setHead(head).setBody(body).build();
		log.info(base.toString());
		socketChannel.writeAndFlush(base);
		Thread.sleep(2000);
		group.shutdownGracefully().syncUninterruptibly();
	}

}
