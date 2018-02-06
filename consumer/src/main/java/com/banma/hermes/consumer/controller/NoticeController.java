package com.banma.hermes.consumer.controller;

import com.banma.hermes.consumer.config.MessageWebSocketHandler;
import com.banma.hermes.consumer.domain.WebMessage;
import com.banma.hermes.consumer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-11 15:53
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Controller
public class NoticeController {

    @Autowired
    MessageWebSocketHandler messageWebSocketHandler;

    @Autowired
    MessageService messageService;

    /*模拟页面接受消息通信*/
    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    //用请求的方式模拟推送消息的时候
    @PostMapping(value = "/send")
    @ResponseBody
    public String notice(@RequestBody WebMessage webMessage) {
        try {
            if(webMessage!=null){
                messageService.getAndSendMessage(webMessage);
            }
            return "200";
        }
        catch (Exception e){
            return "500";
        }
    }




}