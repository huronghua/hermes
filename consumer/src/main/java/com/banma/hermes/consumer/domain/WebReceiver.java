package com.banma.hermes.consumer.domain;

public class WebReceiver {
    private Integer id;

    private String messageId;

    private Integer receiverId;

    private String topic;

    private Integer status;

    private String createTime;

    private String updateTime;

    public WebReceiver(Integer id, String messageId, Integer receiverId, String topic, Integer status, String createTime, String updateTime) {
        this.id = id;
        this.messageId = messageId;
        this.receiverId = receiverId;
        this.topic = topic;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public WebReceiver() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}