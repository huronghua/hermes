package com.banma.hermes.consumer.service;

import com.alibaba.rocketmq.client.exception.MQClientException;


/**
 * Created by banma on 2017/11/24.
 */
public interface ConsumerService {

    /**
     *start a consumer,subscibe dingding message and excute
     * @throws MQClientException if there is any client error.
     */
    void sendDingDingMessage() throws MQClientException;

    /**
     *start a consumer,subscibe sms message and excute
     * @throws MQClientException if there is any client error.
     */
    void sendSMSMessage() throws MQClientException;

}
