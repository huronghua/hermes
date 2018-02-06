package com.banma.hermes.producer.domain;

import java.util.List;

/**
 * Created by banma on 2017/12/19.
 */
public class WebMessage extends MessageSend {

    private List<Integer> receiverIds;

    private String topic;

    public List<Integer> getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(List<Integer> receivers) {
        this.receiverIds = receivers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
