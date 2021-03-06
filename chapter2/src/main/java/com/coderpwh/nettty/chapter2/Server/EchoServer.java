package com.coderpwh.nettty.chapter2.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Created by pengwenhao on 2021/11/23.
 */
@Slf4j
public class EchoServer {

     
    private final int port;


    public EchoServer(int port) {
        this.port = port;
    }


    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            log.info("Usage" + EchoServer.class.getSimpleName() + "<port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        new EchoServer(port).start();


    }

    public void start() throws Exception {

        final EchoServerHandler serverHandler = new EchoServerHandler();

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();

            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            ChannelFuture f = b.bind().sync();

            log.info(EchoServer.class.getName() +
                    " started and listening for connections on " + f.channel().localAddress());

            f.channel().closeFuture().sync();

        } catch (Exception e) {
            log.info("当前错误信息为:{}", e.getMessage());
        } finally {
            group.shutdownGracefully().sync();
        }

    }



}
