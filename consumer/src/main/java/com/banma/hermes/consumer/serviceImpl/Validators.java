package com.banma.hermes.consumer.serviceImpl;

import com.banma.hermes.consumer.Utils.ResultEnum;
import com.banma.hermes.consumer.domain.MessageReceive;
import com.banma.hermes.consumer.domain.WebMessage;
import com.banma.hermes.consumer.exception.ConsumerException;
import com.banma.hermes.consumer.respository.SystemInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by banma on 2017/12/4.
 */
@Component
public class Validators {

    @Autowired
    static SystemInfoMapper systemInfoMapper;


    public static void checkMessage(MessageReceive message) throws ConsumerException{

        if (null == message.getMessageId()){
            throw new ConsumerException(ResultEnum.MESSAGEID_IS_NULL);
        }
        if (null == message.getContext()){
            throw new ConsumerException(ResultEnum.MESSAGEBODY_IS_NULL);
        }
        if (null == message.getType()){
            throw new ConsumerException(ResultEnum.MESSAGETYPE_IS_NULL);
        }
        if (null == message.getIsSequence()){
            throw new ConsumerException(ResultEnum.IS_SEQUENCE);
        }
        if (null == message.getSystemId() ){
            throw new ConsumerException(ResultEnum.SYSTEM_ID_NULL);
        }
    }

    public static void checkWebMessage(WebMessage webMessage){
        if (null == webMessage.getMessageId()){
            throw new ConsumerException(ResultEnum.MESSAGEID_IS_NULL);
        }
        if (null == webMessage.getSystemId() ){
            throw new ConsumerException(ResultEnum.SYSTEM_ID_NULL);
        }
        if (null == webMessage.getIsSequence()){
            throw new ConsumerException(ResultEnum.IS_SEQUENCE);
        }
        if (null == webMessage.getContext()||"".equals(webMessage.getContext())){
            throw new ConsumerException(ResultEnum.MESSAGEBODY_IS_NULL);
        }
        if (null == webMessage.getReceiverIds()||webMessage.getReceiverIds().size() == 0){
            throw new ConsumerException(ResultEnum.RECEIVE_IS_NULL);
        }

    }
}
