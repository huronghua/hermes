package com.banma.hermes.consumer.Utils;

import com.alibaba.fastjson.JSONObject;
import com.banma.hermes.consumer.domain.Email;
import com.banma.hermes.consumer.domain.MessageReceive;
import com.banma.hermes.consumer. exception.ConsumerException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by banma on 2017/12/11.
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    public void email2Redis(Email email){

        try {
            redisTemplate.opsForValue().set(email.getId(), JSONObject.toJSONString(email));
            redisTemplate.expire(email.getId(),1, TimeUnit.DAYS);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ConsumerException(ResultEnum.STRING_REDIS_TEMPLATE_EMAIL_ERROR);
        }
    }

    public boolean hasEmail(Email email){
        String emailBody = redisTemplate.opsForValue().get(email.getId());
        if (null != emailBody&& !"".equals(emailBody)){
            return true;
        }else
            return false;
    }

    public void message2Redis(MessageReceive messageReceive) throws ConsumerException{

        try {
            redisTemplate.opsForValue().set(messageReceive.getMessageId(), JSONObject.toJSONString(messageReceive));
            redisTemplate.expire(messageReceive.getMessageId(),1, TimeUnit.DAYS);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ConsumerException(ResultEnum.STRING_REDIS_TEMPLATE_ERROR);
        }
    }
}
