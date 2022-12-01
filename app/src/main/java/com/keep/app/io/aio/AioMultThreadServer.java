package com.keep.app.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AioMultThreadServer {


    /**
     * AIO 观察者模式，   CompletionHandler： 回调函数，由操作系统调用
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService,1);

        final AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open(threadGroup).bind(new InetSocketAddress(8888));
        channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                channel.accept(null,this);
                try {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    client.read(buffer, buffer,new CompletionHandler<Integer,ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            System.out.println(new String(attachment.array(),0,result));
                            client.write(ByteBuffer.wrap("hello-client".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        while (true){
            Thread.sleep(1000);
        }
    }
}
