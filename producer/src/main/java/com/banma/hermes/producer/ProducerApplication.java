package com.banma.hermes.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.banma.hermes.producer.domain.ProducerSingleton;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;

/**
 * Created by banma on 2017/11/24.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.banma.hermes.producer")
@MapperScan("com.banma.hermes.producer.respository")
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @PreDestroy
    public void destory() throws MQClientException {
        DefaultMQProducer producer = ProducerSingleton.getSingleton().getProducer();
        producer.shutdown();
    }
}
