package com.banma.hermes.consumer.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.banma.hermes.consumer.Utils.*;
import com.banma.hermes.consumer.domain.MessageReceive;
import com.banma.hermes.consumer.domain.WebMessage;
import com.banma.hermes.consumer.exception.ConsumerException;
import com.banma.hermes.consumer.respository.MessageReceiveMapper;
import com.banma.hermes.consumer.respository.SystemInfoMapper;
import com.banma.hermes.consumer.service.MessageService;
import com.banma.hermes.consumer.service.WebProcessor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by banma on 2017/12/12.
 */
@Service
public class WebImpl implements WebProcessor {

    @Value("${consumer.nameSrvAddress}")
    private String nameSrvAddress;

    @Value("${consumer.webTopic}")
    private String webTopic;

    @Value("${consumer.webGroup}")
    private String webGroup;

    @Value("${consumer.WebsocketUrl}")
    private String websocketUrl;

    @Autowired
    MessageReceiveMapper messageReceiveMapper;

    @Autowired
    MessageService messageService;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(WebImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendWebMessage() throws MQClientException {
        /**
         * 创建consumer并订阅webTopic
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(webGroup);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr(nameSrvAddress);
        consumer.subscribe(webTopic, "*");
        /**
         * 创建监听器，监听此topic上的消息并发送给websocket客户端
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt temp : msgs) {
                    String messageId = null;
                    try {
                        /**
                         *  判断消息重发次数，若是二次消费此消息则直接返回，不再重新尝试消费
                         */
                        if (temp.getReconsumeTimes() == 1){
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        /**
                         *  传入消息转换为webMessage
                         */
                        WebMessage webMessage = TransUtil.messageExt2webMessage(temp);
                        messageId = webMessage.getMessageId();
                        /**
                         * 校验消息
                         */
                        Validators.checkWebMessage(webMessage);
                        /**
                         * 发送http请求到websocket服务
                         */
                        messageService.getAndSendMessage(webMessage);
                        /**
                         * 根据返回信息判断websoucket是否正确接收到消息并更新消息状态
                         * 消息状态：0.发送失败；1.发送成功未确认；2.发送成功已确认；3.发送成功处理失败；4.发送失败不重发；5.重发失败消息
                         */
                        messageReceiveMapper.updateByMessageId(new MessageReceive(null,messageId,null,null,null,2,null,null,DateUtil.getDateTimeFormat(new Date()),null,null,null));
                    } catch (ConsumerException e) {
                        messageReceiveMapper.updateByMessageId(new MessageReceive(null,messageId,null,null,null,3,null,null,DateUtil.getDateTimeFormat(new Date()),null,null,null));
                        e.printStackTrace();
                    } catch (Exception e){
                        messageReceiveMapper.updateByMessageId(new MessageReceive(null,messageId,null,null,null,3,null,null,DateUtil.getDateTimeFormat(new Date()),null,null,null));
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
