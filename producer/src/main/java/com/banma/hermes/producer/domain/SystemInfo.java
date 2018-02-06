package com.banma.hermes.producer.domain;

import javax.validation.constraints.NotNull;

public class SystemInfo {

    private Integer systemId;

    @NotNull(message = "topic can not be null!")
    private String webTopic;

    private String emailAddress;

    private String emailKey;

    private String dingdingCount;

    private String dingdingKey;

    private String smsNumber;

    private String smsKey;

    private String createTime;

    private String updateTime;

    private Integer deleteFlag;

    public SystemInfo(Integer systemId, String webTopic, String emailAddress, String emailKey, String dingdingCount, String dingdingKey, String smsNumber, String smsKey, String createTime, String updateTime, Integer deleteFlag) {
        this.systemId = systemId;
        this.webTopic = webTopic;
        this.emailAddress = emailAddress;
        this.emailKey = emailKey;
        this.dingdingCount = dingdingCount;
        this.dingdingKey = dingdingKey;
        this.smsNumber = smsNumber;
        this.smsKey = smsKey;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteFlag = deleteFlag;
    }

    public SystemInfo() {
        super();
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getWebTopic() {
        return webTopic;
    }

    public void setWebTopic(String webTopic) {
        this.webTopic = webTopic == null ? null : webTopic.trim();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress == null ? null : emailAddress.trim();
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey == null ? null : emailKey.trim();
    }

    public String getDingdingCount() {
        return dingdingCount;
    }

    public void setDingdingCount(String dingdingCount) {
        this.dingdingCount = dingdingCount == null ? null : dingdingCount.trim();
    }

    public String getDingdingKey() {
        return dingdingKey;
    }

    public void setDingdingKey(String dingdingKey) {
        this.dingdingKey = dingdingKey == null ? null : dingdingKey.trim();
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber == null ? null : smsNumber.trim();
    }

    public String getSmsKey() {
        return smsKey;
    }

    public void setSmsKey(String smsKey) {
        this.smsKey = smsKey == null ? null : smsKey.trim();
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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}