package com.banma.hermes.producer.domain;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by banma on 2017/12/25.
 */
@Component
public class ProducerSingleton {

    private static String group;

    private static String instanceName;

    private static String nameSrvAddress;

    @Value("${producer.group}")
    public void setGroup(String group) {
        this.group = group;
    }

    @Value("${producer.InstanceName}")
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    @Value("${producer.nameSrvAddress}")
    public void setNameSrvAddress(String nameSrvAddress) {
        this.nameSrvAddress = nameSrvAddress;
    }

    private volatile static ProducerSingleton producerSingleton;
    private ProducerSingleton(){}

    private static DefaultMQProducer producer;

    public static ProducerSingleton getSingleton() throws MQClientException {
        if (producerSingleton == null) {
            synchronized (ProducerSingleton.class) {
                if (producerSingleton == null) {
                    producerSingleton = new ProducerSingleton();
                    producer = new DefaultMQProducer(group);
                    producer.setNamesrvAddr(nameSrvAddress);
                    producer.setInstanceName(instanceName);
                    producer.start();
                }
            }
        }
        return producerSingleton;
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }


}
