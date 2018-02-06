package com.banma.hermes.producer.respository;

import com.banma.hermes.producer.domain.WebReceiver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebReceiverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WebReceiver record);

    int insertSelective(WebReceiver record);

    WebReceiver selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebReceiver record);

    int updateByPrimaryKey(WebReceiver record);

    void insertWebReceiverList(@Param("webReceivers") List<WebReceiver> webReceivers);
}