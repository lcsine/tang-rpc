package com.tang.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class MyChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //处理收到的数据，并反馈消息到客户端
        ByteBuf in = (ByteBuf) msg;
        System.out.println("收到客户端发过来的消息--->"+in.toString(CharsetUtil.UTF_8));
        //写入并发送消息到远端（客户端）
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("你好，我是服务端，我已经收到你的消息----->",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
