package com.banma.hermes.consumer.service;


import com.banma.hermes.consumer.domain.MessageSend;
import com.banma.hermes.consumer.domain.WebMessage;

import java.util.List;

public interface MessageService {
    void getAndSendMessage(WebMessage webMessage);

    List<MessageSend> sendAndUpdateFailureMessage(String userId, String topic);

    void updateMessageStatusSuccessByMessageId(String messageId, List<String> sendSuccessIdList);
}
