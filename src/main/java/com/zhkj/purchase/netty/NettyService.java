package com.zhkj.purchase.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author Dream
 *  Netty的服务
 *
 */
public class NettyService {

    public NettyService() {
//      new Thread(() -> {
            System.out.println("启动Netty!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new MyChannelInitializer());
                Channel channel = serverBootstrap.bind(9099).sync().channel();
                channel.closeFuture().sync();
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
//        }).start();
    }

}

