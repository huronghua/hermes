package com.banma.hermes.consumer.service;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.banma.hermes.consumer.exception.ConsumerException;

/**
 * Created by banma on 2017/12/12.
 */
public interface WebProcessor {
    /**
     *start a consumer,subscibe web message and excute
     * @throws MQClientException if there is any client error.
     */
    void sendWebMessage() throws MQClientException,ConsumerException;
}
