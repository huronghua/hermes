package com.banma.hermes.consumer.serviceImpl;

import com.banma.hermes.consumer.Utils.TransUtil;
import com.banma.hermes.consumer.respository.MessageReceiveMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * Created by banma on 2017/11/28.
 */
public class ScheduledTask {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    MessageReceiveMapper messageReceiveMapper;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 20 10 * * ?")
    public void cronJob() {
        Set<String> a = redisTemplate.keys("*");
        logger.info("scheduled task excute！");
        for (String temp : a) {
            String body = redisTemplate.opsForValue().get(temp);
            messageReceiveMapper.insert(TransUtil.StringToJava(body));
        }
    }
}
