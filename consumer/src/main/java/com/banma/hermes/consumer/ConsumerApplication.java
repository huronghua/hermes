package com.banma.hermes.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by banma on 2017/11/27.
 */
@EnableWebSocket
@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = "com.banma")
@MapperScan("com.banma.hermes.consumer.respository")
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
