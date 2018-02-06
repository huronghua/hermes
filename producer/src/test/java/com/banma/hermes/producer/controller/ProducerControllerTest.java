package com.banma.hermes.producer.controller;

import com.banma.hermes.producer.ProducerApplication;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by banma on 2017/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
@WebAppConfiguration
public class ProducerControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    @Test
    @Transactional
    @Rollback
    public void sendWeb() throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        Gson gson = new Gson();
        map.put("messageId",22);
        map.put("systemId",1);
        map.put("isSequence",1);
        map.put("context","测试1");
        map.put("receiverIds",list);
        System.out.println(gson.toJson(map).toString());
        mockMvc.perform((post("/producer/sendWeb").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(map).toString())))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

}