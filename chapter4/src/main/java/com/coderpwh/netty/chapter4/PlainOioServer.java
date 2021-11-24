 package com.coderpwh.netty.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 阻塞式OIO(非netty方式)
 *
 * @author pengwenhao
 * @date 2021/11/24
 */
@Slf4j
public class PlainOioServer {


    public void serve(int port) throws IOException {

        final ServerSocket socket = new ServerSocket(port);

        try {
            for (; ; ) {

                final Socket clientSocket = socket.accept();
                log.info("accepted connection from" + clientSocket);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
