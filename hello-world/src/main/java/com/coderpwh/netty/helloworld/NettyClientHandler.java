package com.coderpwh.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    /***
     * 连接上服务的回调
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接上服务器.....");
        ctx.writeAndFlush(Unpooled.copiedBuffer("哈哈 你好呀!!!", CharsetUtil.UTF_8));
    }


    /**
     * 读取服务端返回的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("服务端返回的消息:" + buf.toString(CharsetUtil.UTF_8));
    }

}
