package com.banma.hermes.consumer.respository;

import com.banma.hermes.consumer.domain.MessageReceive;

public interface MessageReceiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageReceive record);

    int insertSelective(MessageReceive record);

    MessageReceive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageReceive record);

    int updateByPrimaryKeyWithBLOBs(MessageReceive record);

    int updateByPrimaryKey(MessageReceive record);

    int updateByMessageId(MessageReceive record);
}