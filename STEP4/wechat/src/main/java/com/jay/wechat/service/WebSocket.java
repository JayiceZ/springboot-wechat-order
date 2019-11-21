package com.jay.wechat.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ServerEndpoint("/websocket")
public class WebSocket {
    private Session session;

    private static CopyOnWriteArrayList<WebSocket> webSocketSet = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);
        System.out.println("有新的连接,总数为:"+webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        System.out.println("连接断开，总数为："+webSocketSet.size());
    }

    @OnMessage
    public void onMessgae(String msg){
        System.out.println("websocket消息：收到客户端发来的消息"+ msg);
    }

    //广播发送消息
    public void sengMsg(String msg){
        for (WebSocket webSocket:webSocketSet){
            System.out.println("websocket广播消息："+ msg);
            try {
                webSocket.session.getBasicRemote().sendText(msg);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
