package com.keep.app.io.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 200 并发
 * 普通NIO 吞吐量 2.3/sec
 * 线程池NIO 吞吐量 130/sec
 */
@Slf4j
public class NioWithSelectorServer {

    public static final String HTTP_SEPARATOR = "\r\n";

    public static final int DEFAULT_PORT = 8888;

    public static final String DEFAULT_HOST = "localhost";

    public static final int BACK_LOG = 1024; // linux accept 队列数量

    public static final ThreadPoolExecutor executor = buildThreadPool();

    @SneakyThrows
    public static void main(String[] args) {
        try {
            ServerSocketChannel nioServer = ServerSocketChannel.open();
            nioServer.socket().bind(new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT), BACK_LOG);
            nioServer.configureBlocking(false);
            Selector selector = Selector.open();
            nioServer.register(selector, SelectionKey.OP_ACCEPT);
            log.info("nio server start .....");
            while (true) {
                selector.select();//阻塞方法
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //每个client注册到 server 都会分配一个key，selector 根据key 判断事件类型  连接/读写
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    handle(key);
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private static void handle(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
            log.info("accept 事件");
        } else if (key.isReadable()) {
            log.info("read 事件");
        } else if (key.isWritable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            Thread.sleep(500);
            String sendMsg = "<h1>hello server,I am NIO !!!</h1>";
            sc.write(ByteBuffer.wrap(buildHttpResp(sendMsg).getBytes()));
            sc.close();
            log.info("write 事件");
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
