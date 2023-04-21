package com.example.nft.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author wangyixiong
 * @Date 2023/4/11 下午10:41
 * @PackageName:com.example.nft.controller
 * @ClassName: WebSocket
 * @Description: TODO
 * @Version 1.0
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/{phone}")
public class WebSocket {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private Session session;

    private String phone;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<WebSocket>();

    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    /**
     * 连接成功调用方法
     * @param session
     * @param phone
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "phone") String phone) {
        System.out.println("phone: " + phone);
        System.out.println(session);
        try {
            this.session = session;
            this.phone = phone;
            webSockets.add(this);
            sessionPool.put(phone,session);

            // 用户手机号加入session中
            session.getUserProperties().put("phone", phone);
            log.info("[websocket have a new connection], sum is: " + webSockets.size());
        }catch(Exception e){

        }
    }

    /**
     * 连接断开
     */
    @OnClose
    public void OnClose(){
        try {
            webSockets.remove(this);
            sessionPool.remove(this.phone);
            log.info("[websocket have a connection is down] now,  sum is: " + webSockets.size());
        } catch (Exception e){

        }
    }


    /**
     * 收到消息后调用的方法
     * @param
     */
    @OnMessage
    public void OnMessage(Session session,String data){
        System.out.println("发送人id：" + session.getUserProperties().get("phone"));

        String sender = (String) session.getUserProperties().get("phone");

        log.info("[websocket　接收到消息： ]" + data);
        JSONObject jsonObject = JSON.parseObject(data);
        String receiver = jsonObject.getString("phone");
        String message = (String) jsonObject.get("message");
        if(jsonObject.get("phone").equals("")){
            sendAllMessages((String) jsonObject.get("message"));
        }else{
            sendOneMessage(sender ,receiver, message);
        }
    }

//  {
//  "phone":"efg",
//  "message":"hello, abd"
//  }
    /**
     * 发送错误
     * @param
     */
    @OnError
    public void  OnError(Session session, Throwable error){
        log.error("用户错误，原因：" + error.getMessage());
        error.printStackTrace();
    }

    // 广播消息
    public void sendAllMessages(String  message){
        log.info("[websocket广播消息]：" + message);
        for(WebSocket webSocket: webSockets){
            try {
                if(webSocket.session.isOpen()){
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //单点消息(单人)
    public void sendOneMessage(String sender,String receiver, String message){
        Session session = sessionPool.get(receiver);

        if(session != null && session.isOpen()){
            try {
                log.info(sender + " 发送消息给 " + receiver + " 单点消息：" + message);
                session.getAsyncRemote().sendText(message);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        String key  = "S" + sender + "R" + receiver;
        redisTemplate.opsForList().rightPush(key, message);
    }


    // 单点消息(多人）
    public void sendMoreMessage(String[] phones, String message){
        for (String phone : phones){
            Session session = sessionPool.get(phone);
            if(session != null && session.isOpen()){
                try {
                    log.info("单点多人消息：　" + message);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}