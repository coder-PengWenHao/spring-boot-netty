package com.coderpwh.netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClient {

    public static void main(String[] args) {

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            log.info("客户端准备就绪，随时可以起飞了");

            // 连接服务
            ChannelFuture future = bootstrap.connect("localhost", 6666).sync();

            // 关闭服务
            future.channel().closeFuture().sync();

        } catch (Exception e) {

            log.error("错误信息为:{}", e.getMessage());
        } finally {
            eventExecutors.shutdownGracefully();
        }

    }


}
