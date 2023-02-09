package com.keep.app.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSingleThreadServer {

    public static final String HTTP_SEPARATOR = "\r\n";

    public static final int DEFAULT_PORT = 8888;

    public static final String DEFAULT_HOST = "localhost";

    public static final int BACK_LOG = 1024; // linux accept 队列数量

    public static void main(String[] args) {
        try {
            ServerSocketChannel nioServer = ServerSocketChannel.open();
            nioServer.socket().bind(new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT));
            nioServer.configureBlocking(false);// 设置不阻塞
            Selector selector = Selector.open();
            nioServer.register(selector, SelectionKey.OP_ACCEPT);  // 连接
            while (true) {
                selector.select();// 阻塞方法
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                /**
                 * 每个client注册到 server 都会分配一个key，selector 根据key 判断事件类型  连接/读写
                 */
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    handle(key);
                }
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
            socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
        } else if (key.isWritable()) {
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
}
