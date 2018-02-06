package com.banma.hermes.producer.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.aliyun.oss.OSSClient;
import com.banma.hermes.producer.domain.*;
import com.banma.hermes.producer.exception.ProducerException;
import com.banma.hermes.producer.respository.MessageSendMapper;
import com.banma.hermes.producer.respository.SystemInfoMapper;
import com.banma.hermes.producer.respository.WebReceiverMapper;
import com.banma.hermes.producer.service.ProducerService;
import com.banma.hermes.producer.utils.DateUtil;
import com.banma.hermes.producer.utils.ResultEnum;
import com.banma.hermes.producer.utils.ResultUtil;
import com.banma.hermes.producer.utils.Validators;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by banma on 2017/11/24.
 */

@Service
public class ProducerServiceImpl implements ProducerService {

    @Value("${producer.orderId}")
    private String orderId;

    @Value("${producer.nameSrvAddress}")
    private String nameSrvAddress;

    @Value("${producer.webTopic}")
    private String webTopic;

    @Value("${producer.emailTopic}")
    private String emailTopic;

    @Value("${producer.dingdingTopic}")
    private String dingdingTopic;

    @Value("${producer.SMSTopic}")
    private String SMSTopic;

    @Value("${producer.webCode}")
    private int webCode;

    @Value("${producer.emailCode}")
    private int emailCode;

    @Value("${producer.dingdingCode}")
    private int dingdingCode;

    @Value("${producer.SMSCode}")
    private int SMSCode;

    @Value("${producer.tag}")
    private String tag;

    @Value("${producer.group}")
    private String group;

    @Value("${producer.InstanceName}")
    private String InstanceName;

    @Value("${producer.bucketName}")
    private String bucketName;

    @Value("${aliyunoss.accesskeyid}")
    protected String accesskeyid;

    @Value("${aliyunoss.accesskeysecret}")
    protected String accesskeysecret;

    @Value("${aliyunoss.endpoint}")
    protected String endpoint;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SystemInfoMapper systemInfoMapper;

    @Autowired
    private MessageSendMapper messageSendMapper;

    @Autowired
    private WebReceiverMapper webReceiverMapper;

    @Autowired
    Validators validators;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Override
    public ResponseData send(MessageSend message) throws MQClientException, ProducerException, RemotingException, InterruptedException, MQBrokerException {
        /**
         * 校验消息，检查消息各项是否为空
         */
        validators.checkMessage(message);
        /**
         * 判断缓存中是否有这条消息，如果有则抛出消息重复异常
         */
        Set<String> a = redisTemplate.keys(message.getMessageId());
        if (null != a && a.size() != 0) {
            throw new ProducerException(ResultEnum.DUPLICATE_MESSAGE);
        }
        /**
         * 初始化生产者并开启
         */
        DefaultMQProducer producer = this.initProducer();
        producer.start();
        String topic = null;
        Integer massageType = message.getType();
        if (massageType.equals(webCode)) {
            topic = webTopic;
        }
        if (massageType.equals(dingdingCode)) {
            topic = dingdingTopic;
        }
        if (massageType.equals(SMSCode)) {
            topic = SMSTopic;
        }
        Message msg = new Message(topic,
                tag,
                JSONObject.toJSONString(message).getBytes()
        );
        SendResult sendResult = new SendResult();
        if (message.getIsSequence().equals(0)) {
            sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Random random = new Random();
                    return mqs.get(random.nextInt(4));
                }
            }, orderId);
        } else if (message.getIsSequence().equals(1)) {
            sendResult = producer.send(msg);
        }
        producer.shutdown();
        logger.info(sendResult.toString());
        if (sendResult.getSendStatus().name().equals("SEND_OK")) {
            redisTemplate.opsForValue().set(message.getMessageId(), JSONObject.toJSONString(message));
            redisTemplate.expire(message.getMessageId(), 1, TimeUnit.DAYS);
            return ResultUtil.success(sendResult);
        } else {
            throw new ProducerException(ResultEnum.MESSAGE_SEND_FAILD);
        }
    }

    @Override
    public ResponseData sendEmail(Email email) throws RemotingException, InterruptedException, MQBrokerException, MQClientException, IOException {

        email.setFileInfo(this.uploadOss(email));
        email.setFile(null);

        DefaultMQProducer producer = this.initProducer();
        producer.start();
        Message msg = new Message(emailTopic,
                tag,
                JSONObject.toJSONString(email).getBytes());
        SendResult sendResult;
        try {
            Set<String> a = redisTemplate.keys(email.getId());
            if (null != a && a.size() != 0) {
                throw new ProducerException(ResultEnum.DUPLICATE_MESSAGE);
            }
            if (email.getIsSequence() == 1) {
                sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        return mqs.get(0);
                    }
                }, orderId);
            } else {
                sendResult = producer.send(msg);
            }
            if (sendResult.getSendStatus().name().equals("SEND_OK")) {
                redisTemplate.opsForValue().set(email.getId(), JSONObject.toJSONString(email));
                redisTemplate.expire(email.getId(), 1, TimeUnit.DAYS);
                return ResultUtil.success(sendResult);
            } else {
                throw new ProducerException(ResultEnum.MESSAGE_SEND_FAILD);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
            throw new ProducerException(ResultEnum.MESSAGE_SEND_FAILD);
        } finally {
            producer.shutdown();
        }
    }

    private DefaultMQProducer initProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(group+UUID.randomUUID().toString().replaceAll("-", ""));
        producer.setNamesrvAddr(nameSrvAddress);
        producer.setInstanceName(InstanceName);
        return producer;
    }


    //upload file to oss
    private List<FileInfo> uploadOss(Email email) {
        String endpoint = this.endpoint;
        String accessKeyId = this.accesskeyid;
        String accessKeySecret = this.accesskeysecret;
        String bucket = this.bucketName;
        List<MultipartFile> mfile = email.getFile();
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            if (mfile != null) {
                for (MultipartFile tempFile : mfile) {
                    if (tempFile.getSize() != 0) {
                        ossClient.putObject(bucket, tempFile.getOriginalFilename(), tempFile.getInputStream());
                        Date expiration = new Date(System.currentTimeMillis() + 3600 * 24000);
                        String newurl = ossClient.generatePresignedUrl(bucket, tempFile.getOriginalFilename(), expiration).toString();
                        FileInfo fileInfo = new FileInfo(tempFile.getOriginalFilename(), newurl);
                        fileInfos.add(fileInfo);
                    }
                }
            } else {
                fileInfos = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProducerException(ResultEnum.OSS_UPLOAD_FAILD);
        } finally {
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }
        return fileInfos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData sendWebMessage(WebMessage webMessage) throws MQClientException, ProducerException, RemotingException, InterruptedException, MQBrokerException {
        /**
         * 校验消息，检查消息各项是否为空
         */
        validators.checkWebMessage(webMessage);
        /**
         * 校验消息是否重复
         */
        MessageSend messageSend = messageSendMapper.selectByMessageId(webMessage.getMessageId());
        if (null != messageSend) {
            throw new ProducerException(ResultEnum.DUPLICATE_MESSAGE);
        }
        /**
         * 根据systemId获取topic
         */
        SystemInfo systemInfo = systemInfoMapper.selectByPrimaryKey(webMessage.getSystemId());
        webMessage.setTopic(systemInfo == null ? null : systemInfo.getWebTopic());
        /**
         * 初始化producer并开启
         */
        DefaultMQProducer producer = ProducerSingleton.getSingleton().getProducer();
        SendResult sendResult;
        /**
         * 构建发送的消息,若是isSequence为0是无序消息,随机选择队列发送,为1为有序消息,选择第一个队列发送
         */
        Message msg = new Message(webTopic,
                JSONObject.toJSONString(webMessage).getBytes());
        if (webMessage.getIsSequence() == 0) {
            sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    return mqs.get(new Random().nextInt(4));
                }
            }, orderId);
        } else {
            sendResult = producer.send(msg);
        }
        webMessage.setReceiveTime(DateUtil.getDateTimeFormat(new Date()));
        /**
         * 根据返回结果将消息入库
         */
        if (sendResult.getSendStatus().name().equals("SEND_OK")) {
            webMessage2Mysql(1,webMessage);
            return ResultUtil.success(webMessage);
        } else {
            throw new ProducerException(ResultEnum.MESSAGE_SEND_FAILD);
        }
    }

    /**
     * 将web消息存入DB
     */
    private void webMessage2Mysql(int status, WebMessage webMessage) {

        MessageSend messageSend = webMessage;
        messageSend.setStatus(status);
        List<WebReceiver> webReceiverList = new ArrayList<WebReceiver>();
        /**
         * 构造ReceiverList
         */
        for (Integer i : webMessage.getReceiverIds()) {
            WebReceiver webReceiver = new WebReceiver(null, webMessage.getMessageId(),
                    i, webMessage.getTopic(),0, webMessage.getReceiveTime(), null);
            webReceiverList.add(webReceiver);
        }
        /**
         * 入库
         */
        messageSendMapper.insert(messageSend);
        webReceiverMapper.insertWebReceiverList(webReceiverList);
    }

}
