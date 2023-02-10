package com.keep.app.io.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class AioClient {


    public static void main(String[] args) {
        try {
            AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
            client.setOption(StandardSocketOptions.TCP_NODELAY, true);
            Future<Void> connect = client.connect(new InetSocketAddress("127.0.0.1", 9999));



            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            //向服务端发送消息
            buffer.put(("hello server").getBytes());
            //读取模式
            buffer.flip();
            client.write(buffer);
            buffer.clear();

            //从服务端读取消息
            Future<Integer> future = client.read(buffer);
            int readLenth = future.get();
            //读取模式
            buffer.flip();
            byte[] bytes = new byte[readLenth];
            buffer.get(bytes);
            log.error(new String(bytes, "UTF-8"));
            buffer.clear();

        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println("连接服务器失败,确认服务器正常运行,以及ip设置是否正确.");
            e.printStackTrace();
        }
    }
}
