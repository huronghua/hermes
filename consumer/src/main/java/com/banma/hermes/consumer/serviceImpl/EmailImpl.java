package com.banma.hermes.consumer.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.banma.hermes.consumer.Utils.HttpUtil;
import com.banma.hermes.consumer.Utils.RedisUtil;
import com.banma.hermes.consumer.Utils.ResultEnum;
import com.banma.hermes.consumer.domain.Email;
import com.banma.hermes.consumer.domain.FileInfo;
import com.banma.hermes.consumer.exception.ConsumerException;
import com.banma.hermes.consumer.service.EmailProcessor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by banma on 2017/12/11.
 */
@Service
public class EmailImpl implements EmailProcessor {
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
    private JavaMailSender mailSender;

    @Autowired
    private RedisUtil redisUtil;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(EmailProcessor.class);


    public void sendEmailMessage() throws MQClientException, IOException, JSONException, MailSendException, ConsumerException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(emailGroup);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr(nameSrvAddress);
        try {
            consumer.subscribe(emailTopic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt temp : msgs) {
                    String emailTemp = new String(temp.getBody());
                    JSONObject jasonObject = JSONObject.parseObject(emailTemp);
                    Email email = JSON.toJavaObject(jasonObject, Email.class);
                    if (redisUtil.hasEmail(email)) {
                        logger.info(ResultEnum.MULTIPLICITY_MESSAGE.getCode() + ResultEnum.MULTIPLICITY_MESSAGE.getMessage());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    email(email);
                    redisUtil.email2Redis(email);
                    if (temp.getReconsumeTimes() == 3) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

    private void email(Email email) {
        try {
            System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(email.getSendAddr());
            helper.setTo(email.getReceiveAddr());
            helper.setSubject(email.getTopic());
            helper.setText(email.getContext());
            mimeMessage.setSentDate(new Date());
            if (null != email.getFileInfo()) {
                for (FileInfo fileInfo : email.getFileInfo()) {
                    File file = HttpUtil.downloadFile(fileInfo.getFileName(), fileInfo.getUrl(), "D:");
                    helper.addAttachment(MimeUtility.encodeText(fileInfo.getFileName()), file);
                }
            }
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new ConsumerException(ResultEnum.EMAIL_SEND_FAILD);
        } catch (MailSendException me) {
            me.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConsumerException(ResultEnum.EMAIL_SEND_FAILD);
        }
    }

}
