package com.banma.hermes.producer.controller;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.banma.hermes.producer.domain.*;
import com.banma.hermes.producer.respository.SystemInfoMapper;
import com.banma.hermes.producer.service.ProducerService;
import com.banma.hermes.producer.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;

/**
 * Created by banma on 2017/11/24.
 */
@Controller
@RequestMapping(value = "/producer")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @Autowired
    SystemInfoMapper systemInfoMapper;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData send(@RequestBody MessageSend message) throws Exception {
        return producerService.send(message);
    }


    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData sendTest(@Valid Email email, BindingResult bindingResult) throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        producerService.sendEmail(email);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/sendWeb", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData sendWeb(@RequestBody WebMessage webMessage) throws Exception {
        return producerService.sendWebMessage(webMessage);
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData initSystem(@Valid SystemInfo systemInfo, BindingResult bindingResult) throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(500, bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(systemInfoMapper.insertSelective(systemInfo));
    }

}
