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
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 200 并发
 * 普通NIO 吞吐量 2.3/sec
 * 线程池NIO 吞吐量 130/sec
 */
@Slf4j
public class NioThreadPoolWithSelectorServer {

    public static final String HTTP_SEPARATOR = "\r\n";

    public static final int DEFAULT_PORT = 8888;

    public static final String DEFAULT_HOST = "localhost";

    public static final int BACK_LOG = 1024; // linux accept 队列数量

    private static final int POLLER_NUM = 3;

    private static Poller[] pollers;

    private static ThreadPoolExecutor poolExecutor = buildThreadPool();

    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = createServerSocketChannel();
        initPoller();
        startPoller();
        poolExecutor.prestartAllCoreThreads();
        long count = 0;
        int m = pollers.length - 1;
        log.info("nio server with mult selector start .....");
        for (;;){
            SocketChannel socketChannel = serverSocketChannel.accept();
            pollers[(int) (count++ & m)].addSocketChannel(socketChannel);
        }

    }

    @SneakyThrows
    private static ServerSocketChannel createServerSocketChannel() {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT), BACK_LOG);
        return serverSocketChannel;
    }

    public static void initPoller(){
        pollers = new Poller[POLLER_NUM];
        for (int i = 0; i < pollers.length; i++) {
            Poller poller = new Poller();
            poller.init();
            pollers[i] = poller;
        }
    }

    public static void startPoller(){
        for (int i = 0; i < pollers.length; i++) {
            pollers[i].start();
        }
    }

    public static ThreadPoolExecutor buildThreadPool() {
        return new ThreadPoolExecutor(100, 100, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static class Poller extends Thread{
        private Selector selector;
        private BlockingQueue<SocketChannel> socketChannelBlockingQueue = new LinkedBlockingDeque<>();
        private AtomicBoolean aBoolean = new AtomicBoolean();
        @SneakyThrows
        @Override
        public void run(){
            for (;;){
                for (;;){
                    SocketChannel socketChannel = socketChannelBlockingQueue.poll();
                    if(socketChannel != null){
                        poolExecutor.execute(() ->{
                            try {
                                socketChannel.configureBlocking(false);
                                //do some work
                                //Thread.sleep(500);
                                String sendMsg = "<h1>hello server,I am NIO with mult selector !!!</h1>";
                                socketChannel.register(selector, SelectionKey.OP_WRITE,ByteBuffer.wrap(buildHttpResp(sendMsg).getBytes()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        continue;
                    }
                    break;
                }
                aBoolean.set(true);
                handle();
            }


        }
        private void handle() throws IOException {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //每个client注册到 server 都会分配一个key，selector 根据key 判断事件类型  连接/读写
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    sc.write((ByteBuffer) key.attachment());
                    //sc.close();
                }
                keyIterator.remove();
            }
        }


        @SneakyThrows
        public void init() {
            selector = Selector.open();
        }

        @SneakyThrows
        public void addSocketChannel(SocketChannel socketChannel){
            socketChannelBlockingQueue.put(socketChannel);
            if(aBoolean.get()){
                selector.wakeup();
            }
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
}
