package com.keep.app.io.bio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class  BioServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true){
                Socket s = serverSocket.accept();// 阻塞方法
                new Thread(() ->{
                    handle(s);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle(Socket s) {
        byte[] bytes = new byte[1024];
        int length = 0;
        try {
            length = s.getInputStream().read(bytes);
            String clentMsg = new String(bytes, 0, length);
            System.out.println(String.format("client msg : %s",clentMsg));

            String sendMsg = "hello server,I am server !!!";
//            s.getOutputStream().write(bytes,0,length);
            s.getOutputStream().write(sendMsg.getBytes());
            s.getOutputStream().flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
