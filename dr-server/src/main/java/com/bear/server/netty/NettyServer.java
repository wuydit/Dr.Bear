package com.bear.server.netty;

import com.bear.server.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * @author wuyd
 * 2021/2/25 11:22
 */
@Slf4j
@Service("nettyServer")
public class NettyServer {

    private static final EventLoopGroup BOSS_LOOP_GROUP = new NioEventLoopGroup();

    private static final EventLoopGroup WORK_LOOP_GROUP = new NioEventLoopGroup();

    private static final ServerBootstrap SERVER_BOOTSTRAP = new ServerBootstrap();

    private final NettyServerFilter nettyServerFilter;

    private final NettyConfig nettyConfig;

    public NettyServer(NettyServerFilter nettyServerFilter, NettyConfig nettyConfig) {
        this.nettyServerFilter = nettyServerFilter;
        this.nettyConfig = nettyConfig;
    }

    @Async
    public void run() {
        try {
            SERVER_BOOTSTRAP.group(BOSS_LOOP_GROUP, WORK_LOOP_GROUP);
            SERVER_BOOTSTRAP.channel(NioServerSocketChannel.class);
            // 设置过滤器
            SERVER_BOOTSTRAP.childHandler(nettyServerFilter);
            // 服务器绑定端口监听
            ChannelFuture f = SERVER_BOOTSTRAP.bind(nettyConfig.getPort()).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
            WORK_LOOP_GROUP.shutdownGracefully();
            BOSS_LOOP_GROUP.shutdownGracefully();
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        BOSS_LOOP_GROUP.shutdownGracefully().syncUninterruptibly();
        WORK_LOOP_GROUP.shutdownGracefully().syncUninterruptibly();
        log.warn("Netty Server shutdown success!");
    }
}
