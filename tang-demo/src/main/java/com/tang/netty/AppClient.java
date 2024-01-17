package com.tang.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Handler;

public class AppClient {
    public void run() {
        //定义线程池，EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();

        //启动一个客户端需要一个辅助类，Bootstrap
        try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap = bootstrap.group(group)
                .remoteAddress(new InetSocketAddress(8080))
                //选择初始化一个什么样的channel
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MyChannelHandler2());
                    }
                });
        //尝试连接服务器
        ChannelFuture sync = null;

            sync = bootstrap.connect().sync();
            // 获取channel，并且写出数据
            sync.channel().writeAndFlush(Unpooled.copiedBuffer("hello netty".getBytes(StandardCharsets.UTF_8)));
            //阻塞程序，等到接收消息
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new AppClient().run();
    }
}
