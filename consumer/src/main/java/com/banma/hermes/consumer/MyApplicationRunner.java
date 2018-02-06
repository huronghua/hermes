package com.banma.hermes.consumer;

import com.banma.hermes.consumer.service.WebProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by banma on 2017/11/27.
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {



    @Autowired
    WebProcessor webProcessor;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        webProcessor.sendWebMessage();
    }
}
