package com.banma.hermes.consumer.serviceImpl;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.banma.hermes.consumer.Utils.DateUtil;
import com.banma.hermes.consumer.Utils.RedisUtil;
import com.banma.hermes.consumer.Utils.ResultEnum;
import com.banma.hermes.consumer.Utils.TransUtil;
import com.banma.hermes.consumer.domain.MessageReceive;
import com.banma.hermes.consumer.exception.ConsumerException;
import com.banma.hermes.consumer.service.ConsumerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by banma on 2017/11/24.
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Value("${consumer.nameSrvAddress}")
    private String nameSrvAddress;

    @Value("${consumer.webTopic}")
    private String webTopic;

    @Value("${consumer.emailTopic}")
    private String emailTopic;

    @Value("${consumer.dingdingTopic}")
    private String dingdingTopic;

    @Value("${consumer.SMSTopic}")
    private String SMSTopic;

    @Value("${consumer.webCode}")
    private int webCode;

    @Value("${consumer.emailCode}")
    private int emailCode;

    @Value("${consumer.dingdingCode}")
    private int dingdingCode;

    @Value("${consumer.SMSCode}")
    private int SMSCode;

    @Value("${consumer.tag}")
    private String tag;

    @Value("${consumer.webGroup}")
    private String webGroup;

    @Value("${consumer.emailGroup}")
    private String emailGroup;

    @Value("${consumer.dingdingGroup}")
    private String dingdingGroup;

    @Value("${consumer.SMSGroup}")
    private String SMSGroup;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    RedisUtil redisUtil;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);


    public void sendDingDingMessage() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(dingdingGroup);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr(nameSrvAddress);
        try {
            consumer.subscribe(dingdingTopic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt temp:msgs){
                    MessageReceive messageReceive = TransUtil.messageExt2Java(temp);
                    Validators.checkMessage(messageReceive);
                    /**
                     * dingding处理代码
                     * **/
                    //处理状态0:已成功处理;1:未成功处理
                    messageReceive.setStatus(0);
                    if ((redisTemplate.keys(messageReceive.getMessageId()).size()!=0)){
                        throw new ConsumerException(ResultEnum.MULTIPLICITY_MESSAGE);
                    }
                    Date date = new Date();
                    messageReceive.setReceiveTime(DateUtil.getDateTimeFormat(date));
                    System.out.println(DateUtil.getDateTimeFormat(date));
                    System.out.println("message:"+messageReceive.toString());
                    redisUtil.message2Redis(messageReceive);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

    public void sendSMSMessage() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(SMSGroup);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr(nameSrvAddress);
        consumer.subscribe(SMSTopic, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt temp:msgs){
                    MessageReceive messageReceive = TransUtil.messageExt2Java(temp);
                    Validators.checkMessage(messageReceive);
                    /**
                     * SMS处理代码
                     * **/
                    //处理状态0:已成功处理;1:未成功处理
                    messageReceive.setStatus(0);
                    if ((redisTemplate.keys(messageReceive.getMessageId()).size()!=0)){
                        throw new ConsumerException(ResultEnum.MULTIPLICITY_MESSAGE);
                    }
                    Date date = new Date();
                    messageReceive.setReceiveTime(DateUtil.getDateTimeFormat(date));
                    System.out.println(DateUtil.getDateTimeFormat(date));
                    System.out.println("message:"+messageReceive.toString());
                    redisUtil.message2Redis(messageReceive);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

}
