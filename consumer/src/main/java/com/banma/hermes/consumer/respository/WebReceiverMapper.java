package com.banma.hermes.consumer.respository;

import com.banma.hermes.consumer.domain.MessageSend;
import com.banma.hermes.consumer.domain.WebReceiver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebReceiverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WebReceiver record);

    int insertSelective(WebReceiver record);

    WebReceiver selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebReceiver record);

    int updateByPrimaryKey(WebReceiver record);

    List<MessageSend> getSendFailureMessageByUserIdAndTopic(@Param(value = "userId") String userId, @Param(value = "topic") String topic);

    Integer updateMessageStatusSuccessByMessageId(@Param(value = "messageId") String messageId,@Param(value = "sendSuccessIdList")List<String> sendSuccessIdList );

    Integer updateMessageStatusSuccessByUserIdAndTopic(@Param(value = "userId") String userId, @Param(value = "topic") String topic);
}