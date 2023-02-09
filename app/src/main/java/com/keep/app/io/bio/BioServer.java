package com.keep.app.io.bio;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 200 并发
 * BIO单线程 吞吐量 2.1/sec
 * BIO线程池 吞吐量 132.9/sec
 */
@Slf4j
public class BioServer {

    public static final String HTTP_SEPARATOR = "\r\n";

    public static final int DEFAULT_PORT = 8888;

    public static final int BACK_LOG = 1024; // linux accept 队列数量

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT, BACK_LOG);
            log.info("bio server started .....");
            while (true) {
                Socket s = serverSocket.accept();// 阻塞方法
                ThreadPoolExecutor executor = buildThreadPool();
                executor.execute(() -> response(s));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ThreadPoolExecutor buildThreadPool() {

        return new ThreadPoolExecutor(100, 100, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @SneakyThrows
    private static void response(Socket s) {
        // do some work
        Thread.sleep(500);
        try {
            /*length = s.getInputStream().read(bytes);
            String clentMsg = new String(bytes, 0, length);
            System.out.println(String.format("client msg : %s",clentMsg));*/
            String sendMsg = "<h1>hello server,I am BIO !!!</h1>";
            s.getOutputStream().write(buildHttpResp(sendMsg).getBytes());
            s.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
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
