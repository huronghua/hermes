package com.banma.hermes.producer.respository;

import com.banma.hermes.producer.domain.MessageSend;
import org.apache.ibatis.annotations.Param;

public interface MessageSendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageSend record);

    int insertSelective(MessageSend record);

    MessageSend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageSend record);

    int updateByPrimaryKeyWithBLOBs(MessageSend record);

    int updateByPrimaryKey(MessageSend record);

    MessageSend selectByMessageId(@Param("message_id") String messageId);
}