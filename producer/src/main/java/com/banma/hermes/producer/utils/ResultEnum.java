package com.banma.hermes.producer.utils;

/**
 * Created by banma on 2017/12/4.
 */
public enum ResultEnum {


    //producer client error
    MESSAGE_SEND_FAILD(100,"message send fail"),

    //validate
    ILLEGAL_PARAM(1000,"illegal Param!"),
    MESSAGEID_IS_NULL(1001,"message_id can not be null!"),
    MESSAGEBODY_IS_NULL(1002,"message_body can not be null!"),
    MESSAGETYPE_IS_NULL(1003,"message_type can not be null!"),
    IS_SEQUENCE(1004,"is_sequence can not be null!"),
    SYSTEM_ID_NULL(1005,"system_id can not be null!"),
    DINGDING_IS_NULL(1007,"dingding'type can not be null!"),
    PHONENUMBER_IS_NULL(1008,"message'type can not be null!"),
    DUPLICATE_MESSAGE(1009,"duplicate message!"),
    SYSTEM_IS_NOT_EXIT(1010,"system is not exit!"),
    SYSTEM_TOPIC_IS_NULL(1011,"system topic is not exit!"),
    IS_SEQUENCE_ILLEGAL(1012,"isSequence is illegal!"),


    OSS_UPLOAD_FAILD(2000,"oss upload file fail!"),
    RECEIVE_IS_NULL(2001,"receiver can not be null!")

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
