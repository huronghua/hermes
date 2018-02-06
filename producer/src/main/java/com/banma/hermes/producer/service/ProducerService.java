package com.banma.hermes.producer.service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.banma.hermes.producer.domain.Email;
import com.banma.hermes.producer.domain.MessageSend;
import com.banma.hermes.producer.domain.ResponseData;
import com.banma.hermes.producer.domain.WebMessage;
import com.banma.hermes.producer.exception.ProducerException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by banma on 2017/11/24.
 */
public interface ProducerService {
    /**
     * send message
     * @param message Message to send.
     * @return {@link ResponseData} instance to respose result
     * @throws MQClientException if there is any client error.
     */
     ResponseData send(MessageSend message) throws Exception;

    /**
     * send email
     * @param email to send.
     * @return {@link ResponseData} instance to respose result
     */
     ResponseData sendEmail(Email email) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, IOException;

    /**
     * send message
     * @param webMessage Message to send.
     * @return {@link ResponseData} instance to respose result
     * @throws MQClientException if there is any client error.
     */
    ResponseData sendWebMessage(WebMessage webMessage) throws MQClientException, ProducerException, RemotingException, InterruptedException, MQBrokerException;
}
