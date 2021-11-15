package com.coderpwh.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/***
 *  服务端处理器
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {


    /***
     * 读取客户端发送的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //  taskQueue任务队列
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    log.info("当前业务需要处理，请耐心等待");
                } catch (Exception e) {
                    log.error("错误信息为:" + e.getMessage());
                }
            }
        });

    }


    /**
     * 发送消息给客户端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已经收到消息，并给你发送一个问号?", CharsetUtil.UTF_8));
    }

    /**
     * 关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
