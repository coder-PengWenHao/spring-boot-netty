package com.coderpwh.netty.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author pengwenhao
 * @date 2021/11/25
 */
@Slf4j
public class PlainNioServer {

    public void serve(int port) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);

        ServerSocket ss = serverSocketChannel.socket();

        InetSocketAddress address = new InetSocketAddress(port);

        ss.bind(address);

        Selector selector = Selector.open();


        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        for (; ; ) {
            try {
                selector.select();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {

                    ServerSocketChannel server = (ServerSocketChannel) key.channel();

                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());

                    log.info("accepted connection from " + client);
                }

                if (key.isWritable()) {

                    SocketChannel client =
                            (SocketChannel) key.channel();

                    ByteBuffer buffer = (ByteBuffer) key.attachment();

                    while (buffer.hasRemaining()) {
                        if (client.write(buffer) == 0) {
                            break;
                        }
                    }
                    client.close();
                }


            }


        }


    }

}
