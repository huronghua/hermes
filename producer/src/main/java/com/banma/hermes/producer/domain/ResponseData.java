package com.banma.hermes.producer.domain;

public class ResponseData {

    private int code;
    private String msg;
    private Object data;

    public ResponseData(String requestId, int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public ResponseData(String requestId, int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseData() {

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


}
