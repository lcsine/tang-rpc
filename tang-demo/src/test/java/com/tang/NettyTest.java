package com.tang;

import com.tang.netty.AppClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

    @Test
    public void testMessage() throws IOException {
        ByteBuf message = Unpooled.buffer();
        message.writeBytes("ydl".getBytes(StandardCharsets.UTF_8));
        message.writeByte(1);
        message.writeShort(125);
        message.writeInt(256);
        message.writeByte(1);
        message.writeByte(0);
        message.writeByte(2);
        message.writeLong(251455L);
        //用对象流转化为字节数据
        AppClient appClient = new AppClient();
        ByteArrayOutputStream OutStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(OutStream);
        oos.writeObject(appClient);
        byte[] bytes = OutStream.toByteArray();
        message.writeBytes(bytes);
        System.out.println(message);

    }

    @Test
    public void testCompress() throws IOException {
        byte [] buf = new byte[]{12,25,44,23,12,23,25,12,12,12,12,35,35,25};

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bao);

        gzipOutputStream.write(buf);

        gzipOutputStream.finish();
        System.out.println(bao.toByteArray().length+"->>>>"+buf.length);
        System.out.println(Arrays.toString(bao.toByteArray()));
    }
    @Test
    public void testDeCompress() throws IOException {
        byte [] buf = new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, -1, -29, -111, -44, 17, -25, 17, -105, -28, 1, 2, 101, 101, 73, 0, 113, 57, -50, 60, 14, 0, 0, 0};

        ByteArrayInputStream bao = new ByteArrayInputStream(buf);
        GZIPInputStream gzipInputStream = new GZIPInputStream(bao);
        byte[] bytes = gzipInputStream.readAllBytes();
        System.out.println(Arrays.toString(bytes));
    }
}
