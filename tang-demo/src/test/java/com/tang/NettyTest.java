package com.tang;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

public class NettyTest {
    @Test
    public void testCompositeByteBuf() {
        ByteBuf header = Unpooled.buffer();
        ByteBuf body = Unpooled.buffer();

        //通过逻辑组装，非物理拷贝，实现再jvm中的零拷贝
        CompositeByteBuf byteBuf = Unpooled.compositeBuffer();
        byteBuf.addComponents(header, body);

    }

    @Test
    public void testWrapperByteBuf() {
        byte[] buf = new byte[1024];
        byte[] buf2 = new byte[1024];
        ByteBuf byteBuf = Unpooled.wrappedBuffer(buf, buf2,buf);
        System.out.println(byteBuf);
        ByteBuf slice = byteBuf.slice(0, 1);
        ByteBuf slice1 = byteBuf.slice(1, 5);
        System.out.println(slice1);
    }
}
