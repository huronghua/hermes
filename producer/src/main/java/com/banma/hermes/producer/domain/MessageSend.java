package com.banma.hermes.producer.domain;

import java.util.Date;

public class MessageSend {
    private Integer id;

    private String messageId;

    private Integer type;

    private String title;

    private Integer systemId;

    private Integer status;

    private Integer isSequence;

    private String sendTime;

    private String receiveTime;

    private Date createTime;

    private Date updateTime;

    private String context;

    public MessageSend(Integer id, String messageId, Integer type, String title, Integer systemId, Integer status, Integer isSequence, String sendTime, String receiveTime, Date createTime, Date updateTime, String context) {
        this.id = id;
        this.messageId = messageId;
        this.type = type;
        this.title = title;
        this.systemId = systemId;
        this.status = status;
        this.isSequence = isSequence;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.context = context;
    }

    public MessageSend() {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsSequence() {
        return isSequence;
    }

    public void setIsSequence(Integer isSequence) {
        this.isSequence = isSequence;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime == null ? null : sendTime.trim();
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime == null ? null : receiveTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }
}