package com.coderpwh.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyServer {

    public static void main(String[] args) {

        /**
         * 创建两个线程组
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务端启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MyServerHandler());
                        }
                    });
            System.out.println("java技术爱好者的服务端已经准备就绪...");

            // 绑定端口号
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error("异常为:{}", e.getMessage());
        } finally {
            // 关闭线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }


}
