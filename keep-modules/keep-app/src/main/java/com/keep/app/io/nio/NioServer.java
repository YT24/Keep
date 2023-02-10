package com.keep.app.io.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 200 并发
 * 普通NIO 吞吐量 2.3/sec
 * 线程池NIO 吞吐量 130/sec
 */
@Slf4j
public class NioServer {

    public static final String HTTP_SEPARATOR = "\r\n";

    public static final int DEFAULT_PORT = 8888;

    public static final String DEFAULT_HOST = "localhost";

    public static final int BACK_LOG = 1024; // linux accept 队列数量

    @SneakyThrows
    public static void main(String[] args) {
        try {
            ServerSocketChannel nioServer = ServerSocketChannel.open();
            nioServer.socket().bind(new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT),BACK_LOG);
            ThreadPoolExecutor executor = buildThreadPool();
            log.info("nio server start .....");
            while (true) {
                SocketChannel socketChannel = nioServer.accept();
                String sendMsg = "<h1>hello server,I am NIO !!!</h1>";
                executor.execute(() ->{
                    try {
                        // do some work
                        Thread.sleep(500);
                        socketChannel.write(ByteBuffer.wrap(buildHttpResp(sendMsg).getBytes()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } else if (key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.clear();
            int length = sc.read(byteBuffer);
            if(length != -1){
                System.out.println(new String(byteBuffer.array(),0,length));
            }
            ByteBuffer write = ByteBuffer.wrap(("server msg:" + (new String(byteBuffer.array(),0,length).replace("client msg",""))).getBytes());
            sc.write(write);
            sc.close();


        }
    }

    public static String buildHttpResp(String sendMsg) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK").append(HTTP_SEPARATOR);
        stringBuilder.append("connection: Close").append(HTTP_SEPARATOR);
        stringBuilder.append("content-type: text/html").append(HTTP_SEPARATOR);
        stringBuilder.append("content-length: " + sendMsg.length()).append(HTTP_SEPARATOR);
        stringBuilder.append(HTTP_SEPARATOR);
        stringBuilder.append(sendMsg);
        return stringBuilder.toString();

    }

    public static ThreadPoolExecutor buildThreadPool() {
        return new ThreadPoolExecutor(100, 100, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
