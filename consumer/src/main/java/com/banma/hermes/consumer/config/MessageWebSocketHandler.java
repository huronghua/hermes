package com.banma.hermes.consumer.config;

import com.banma.hermes.consumer.domain.MessageSend;
import com.banma.hermes.consumer.domain.WebMessage;
import com.banma.hermes.consumer.service.MessageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-08 13:48
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    MessageService messageService;

    Gson gson = new GsonBuilder().serializeNulls().create();

    //维护一个客户端的在线列表
    private static Map<WebSocketSession,String> clientMap = new HashMap<WebSocketSession, String>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String userId = String.valueOf(webSocketSession.getAttributes().get("userId"));
        System.out.println("Connection established");
        clientMap.put(webSocketSession,userId);
        //检查列表中是否存在该用户的消息
        String topic = webSocketSession.getUri().getPath();
        List<MessageSend> failureMessageList = messageService.sendAndUpdateFailureMessage(userId,topic);
            //组装统一的发送消息得列表
        webSocketSession.sendMessage(new TextMessage(gson.toJson(failureMessageList)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received:" + message.getPayload());
        session.sendMessage(new TextMessage(message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Client has disconnected!");
        clientMap.remove(session);
        super.afterConnectionClosed(session, status);
    }

    /**
     * 测试发送对象消息
     * @param webMessage
     * @throws IOException
     */
    public void sendMessage(WebMessage webMessage) throws IOException {
        //遍历记录的session，取出符合条件的session发送消息
        if (MapUtils.isNotEmpty(clientMap)) {
            Set<Map.Entry<WebSocketSession, String>> entrySet = clientMap.entrySet();
            Iterator<Map.Entry<WebSocketSession, String>> clientIterator = entrySet.iterator();
            //维护一个接收成功用户的列表
            List<String> receiveIdList = new ArrayList<String>();
            while (clientIterator.hasNext()) {
                Map.Entry<WebSocketSession, String> client = clientIterator.next();
                WebSocketSession webSocketSession = client.getKey();
                String userId = client.getValue();
                String topic = webMessage.getTopic();
                if (webSocketSession.isOpen()) {
                    if (webSocketSession.getUri().getPath().equals(topic)) {
                        for (Integer receiveId : webMessage.getReceiverIds()) {
                            if (userId.equals(String.valueOf(receiveId))) {
                                //构造统一的消息返回对象
                                MessageSend messageSend = new MessageSend();
                                messageSend.setId(webMessage.getMessageId());
                                messageSend.setContent(webMessage.getContext());
                                List<MessageSend> messageSendList = new ArrayList<MessageSend>();
                                messageSendList.add(messageSend);
                                webSocketSession.sendMessage(new TextMessage(gson.toJson(messageSendList)));
                                receiveIdList.add(userId);
                            }
                        }
                    }
                } else {
                    //如果客户端不在线就从列表里移除，以防map中出现重复的key
                    clientMap.remove(webSocketSession);
                }
            }
            //更新数据库中已通知的消息状态为发送成功
            if (CollectionUtils.isNotEmpty(receiveIdList)) {
                messageService.updateMessageStatusSuccessByMessageId(webMessage.getMessageId(), receiveIdList);
            }
        }
    }
}