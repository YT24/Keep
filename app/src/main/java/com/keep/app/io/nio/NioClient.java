package com.keep.app.io.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {


    public void start() {
        try  {

            int sendCount = 1;
            //这里最好使用selector处理   这里只是为了写的简单
            while (sendCount <= 5) {
                //连接服务端socket
                SocketChannel socketChannel = SocketChannel.open();
                SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9999);
                socketChannel.connect(socketAddress);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                //向服务端发送消息
                buffer.put(("client msg : " + sendCount).getBytes());
                //读取模式
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();

                //从服务端读取消息
                int readLenth = socketChannel.read(buffer);
                //读取模式
                buffer.flip();
                byte[] bytes = new byte[readLenth];
                buffer.get(bytes);
                System.out.println(new String(bytes, "UTF-8"));
                buffer.clear();


                sendCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Thread.sleep(10000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}