package com.keep.app.io.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioMultThreadServer {

    ExecutorService pool = Executors.newFixedThreadPool(5);

    private Selector selector;


    public static void main(String[] args) throws IOException {

        NioMultThreadServer server = new NioMultThreadServer();
        server.initServer(9999);
        server.listen();

    }

    private void  listen() throws IOException {
        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);// 设置为不阻塞
                    channel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }else if(key.isReadable()){
                    key.interestOps(key.interestOps()&(-SelectionKey.OP_READ));
                    pool.execute(new ThreadHandlerChannel(key));
                }else if(key.isWritable()){

                }
            }

        }
    }

    private void initServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server 启动成功");


    }

}

class ThreadHandlerChannel extends Thread{

    private SelectionKey key;

    public ThreadHandlerChannel(SelectionKey key){
        this.key = key;
    }

    @Override
    public void run(){
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int size = 0;
            while ((size = channel.read(buffer)) > 0 ){
                buffer.flip();
                byteArrayOutputStream.write(buffer.array(),0,size);
                buffer.clear();
            }

            byteArrayOutputStream.close();

            byte[] content = byteArrayOutputStream.toByteArray();
            ByteBuffer writeBuffer = ByteBuffer.allocate(content.length);
            writeBuffer.put(content);
            writeBuffer.flip();
            channel.write(writeBuffer);
            System.out.println(new String(content, "UTF-8"));

           /* if(size == -1 ){
                channel.close();
            }else {
                key.interestOps(key.interestOps() | (-SelectionKey.OP_READ) | (-SelectionKey.OP_WRITE));
                key.selector().wakeup();
            }*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long epochMilli = LocalDateTime.now().toInstant(ZoneOffset.ofHours(+8)).toEpochMilli();
        System.out.println(epochMilli);
    }
}