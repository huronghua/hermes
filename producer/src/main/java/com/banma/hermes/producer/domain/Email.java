package com.banma.hermes.producer.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Created by banma on 2017/12/5.
 */
@Entity
public class Email {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank(message="email id can not be null!")
    private String id;

    private List<MultipartFile> mfile;

    @NotBlank(message="email topic can not be null!")
    private String topic;

    @NotBlank(message="email context can not be null!")
    private String context;

    @NotBlank(message="send email address can not be null!")
    @org.hibernate.validator.constraints.Email(message = "send email address illegal")
    private String sendAddr;

    @NotBlank(message = "receive email address can not be null!")
    @org.hibernate.validator.constraints.Email(message = "receive email address illegal")
    private String receiveAddr;

    public Integer getIsSequence() {
        return isSequence;
    }

    public void setIsSequence(Integer isSequence) {
        this.isSequence = isSequence;
    }

    @NotNull(message = "isSequence can not be null!")
    @Max(value = 1,message = "The maximum can not exceed 1")
    private Integer isSequence;

    private List<FileInfo> fileInfo;


    public List<MultipartFile> getMfile() {
        return mfile;
    }

    public void setMfile(List<MultipartFile> mfile) {
        this.mfile = mfile;
    }

    public List<FileInfo> getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(List<FileInfo> fileInfo) {
        this.fileInfo = fileInfo;
    }

    public List<MultipartFile> getFile() {
        return mfile;
    }

    public void setFile(List<MultipartFile> file) {
        this.mfile = file;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

}
