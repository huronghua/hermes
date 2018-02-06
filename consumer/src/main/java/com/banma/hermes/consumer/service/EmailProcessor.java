package com.banma.hermes.consumer.service;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.banma.hermes.consumer.exception.ConsumerException;

import java.io.IOException;

/**
 * Created by banma on 2017/12/11.
 */
public interface EmailProcessor {

    /**
     *start a consumer,subscibe email message and excute
     * @throws MQClientException if there is any client error.
     * @throws IOException if there is any IO error.
     */
    void sendEmailMessage() throws MQClientException, IOException,ConsumerException;
}
