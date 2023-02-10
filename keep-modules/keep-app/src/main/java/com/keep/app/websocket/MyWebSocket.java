package com.keep.app.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket服务端
 * @author chen jia hao
 */
@Component
@ServerEndpoint(value = "/myWebSocket/{userId}")
@Slf4j
public class MyWebSocket {

    //用来存放每个客户端对应的MyWebSocket对象
    private static CopyOnWriteArraySet<MyWebSocket> user = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private Long requestTime = System.currentTimeMillis();

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        //群发消息
        for (MyWebSocket myWebSocket : user) {
            log.info("请求时间 ->> ->> ->> : "+requestTime);
            myWebSocket.session.getBasicRemote().sendText(session.getId() + "说：" + message);
        }
    }

    @OnOpen
    public void onOpen(Session session,@PathParam("userId")Integer  userId) {
        System.out.println(session.getId() + " open...");
        System.out.println(userId);
        this.session = session;
        user.add(this);
    }

    @OnClose
    public void onClose() {
        System.out.println(this.session.getId() + " close...");
        user.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println(this.session.getId() + " error...");
        error.printStackTrace();
    }
}