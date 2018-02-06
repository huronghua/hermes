package com.banma.hermes.producer.utils;

import com.banma.hermes.producer.domain.MessageSend;
import com.banma.hermes.producer.domain.WebMessage;
import com.banma.hermes.producer.exception.ProducerException;
import com.banma.hermes.producer.respository.SystemInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by banma on 2017/12/4.
 */
@Component
public class Validators {

    @Autowired
    SystemInfoMapper systemInfoMapper;

    public void checkMessage(MessageSend message) throws ProducerException{

        if (null == message.getMessageId()){
            throw new ProducerException(ResultEnum.MESSAGEID_IS_NULL);
        }
        if (null == message.getContext()){
            throw new ProducerException(ResultEnum.MESSAGEBODY_IS_NULL);
        }
        if (null == message.getType()){
            throw new ProducerException(ResultEnum.MESSAGETYPE_IS_NULL);
        }
        if (null == message.getIsSequence()){
            throw new ProducerException(ResultEnum.IS_SEQUENCE);
        }
        if (null == message.getSystemId() ){
            throw new ProducerException(ResultEnum.SYSTEM_ID_NULL);
        }
    }

    public void checkWebMessage(WebMessage webMessage){
        if (null == webMessage.getMessageId()){
            throw new ProducerException(ResultEnum.MESSAGEID_IS_NULL);
        }
        if (null == webMessage.getSystemId() ){
            throw new ProducerException(ResultEnum.SYSTEM_ID_NULL);
        }
        if (null == webMessage.getIsSequence()){
            throw new ProducerException(ResultEnum.IS_SEQUENCE);
        }
        if (webMessage.getIsSequence() != 1&& webMessage.getIsSequence() !=0){
            throw new ProducerException(ResultEnum.IS_SEQUENCE_ILLEGAL);
        }
        if (null == webMessage.getContext()||"".equals(webMessage.getContext())){
            throw new ProducerException(ResultEnum.MESSAGEBODY_IS_NULL);
        }
        if (null == webMessage.getReceiverIds()||webMessage.getReceiverIds().size() == 0){
            throw new ProducerException(ResultEnum.RECEIVE_IS_NULL);
        }
        if (! systemInfoMapper.selectIdList().contains(webMessage.getSystemId())){
            throw new ProducerException(ResultEnum.SYSTEM_IS_NOT_EXIT);
        }
        if (! systemInfoMapper.selectTopicIdList().contains(webMessage.getSystemId())){
            throw new ProducerException(ResultEnum.SYSTEM_TOPIC_IS_NULL);
        }
    }
}
