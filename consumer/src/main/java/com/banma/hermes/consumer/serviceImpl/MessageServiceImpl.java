package com.banma.hermes.consumer.serviceImpl;

import com.banma.hermes.consumer.config.MessageWebSocketHandler;
import com.banma.hermes.consumer.domain.MessageSend;
import com.banma.hermes.consumer.domain.WebMessage;
import com.banma.hermes.consumer.respository.WebReceiverMapper;
import com.banma.hermes.consumer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-11 16:08
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageWebSocketHandler messageWebSocketHandler;

    @Autowired
    WebReceiverMapper webReceiverMapper;

    @Override
    public void getAndSendMessage(WebMessage webMessage) {
        try {
            messageWebSocketHandler.sendMessage(webMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<MessageSend> sendAndUpdateFailureMessage(String userId, String topic) {

        List<MessageSend> messageSendList = webReceiverMapper.getSendFailureMessageByUserIdAndTopic(userId,topic);
        webReceiverMapper.updateMessageStatusSuccessByUserIdAndTopic(userId,topic);
        return  messageSendList;
    }

    @Override
    public void updateMessageStatusSuccessByMessageId(String messageId,List<String> sendSuccessIdList) {
        webReceiverMapper.updateMessageStatusSuccessByMessageId(messageId,sendSuccessIdList);
    }
}