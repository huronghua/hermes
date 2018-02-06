package com.banma.hermes.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-08 13:42
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

@Configuration
@EnableWebSocket
public class MessageWebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(messageWebSocketHandler(),"/orange")
                .addInterceptors(new MessageWebSocketInterceptor());
        /*webSocketHandlerRegistry.addHandler(messageWebSocketHandler(),"/huronghua")
                .addInterceptors(new MessageWebSocketInterceptor());*/
    }

    @Bean
    public MessageWebSocketHandler messageWebSocketHandler(){
        return new MessageWebSocketHandler();
    }
}