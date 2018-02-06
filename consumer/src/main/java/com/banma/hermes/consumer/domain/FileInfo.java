package com.banma.hermes.consumer.domain;

/**
 * Created by banma on 2017/12/7.
 */
public class FileInfo {

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String fileName;

    public FileInfo(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }
    public FileInfo() {
       super();
    }

    private String url;
}
