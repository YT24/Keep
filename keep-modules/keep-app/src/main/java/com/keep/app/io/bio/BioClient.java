package com.keep.app.io.bio;


import java.io.IOException;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) {
        Socket client = null;
        try {
            client = new Socket("127.0.0.1",8888);
            client.getOutputStream().write("hello server,I am client !!!".getBytes());
            client.getOutputStream().flush();

            byte[] b = new byte[1024];
            int length = client.getInputStream().read(b);
            System.out.println(String.format("server msg : %s",new String(b,0,length)));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
