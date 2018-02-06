package com.banma.hermes.consumer.exception;


import com.banma.hermes.consumer.ConsumerApplication;
import com.banma.hermes.consumer.domain.WebMessage;
import com.banma.hermes.consumer.serviceImpl.Validators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by banma on 2017/12/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsumerApplication.class)
@WebAppConfiguration
public class ConsumerExceptionTest {

       @Test
       public void testException(){
        Validators.checkWebMessage(new WebMessage());
       }
}