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

    public static void main(String[] args) {
        try {
            ServerSocketChannel nioServer = ServerSocketChannel.open();
            nioServer.socket().bind(new InetSocketAddress("127.0.0.1", 9999));
            nioServer.configureBlocking(false);// 设置不阻塞
            System.out.println("Server started,listening on :" + nioServer.getLocalAddress());

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
}
