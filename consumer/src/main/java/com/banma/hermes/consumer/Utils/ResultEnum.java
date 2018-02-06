package com.banma.hermes.consumer.Utils;


/**
 * Created by banma on 2017/12/4.
 */
public enum ResultEnum {


    //producer client error
    MESSAGE_SEND_FAILD(100,"message send fail"),
    //validate
    ILLEGAL_PARAM(1000,"illegal Param!"),
    MESSAGEID_IS_NULL(1001,"message_id is null"),
    MESSAGEBODY_IS_NULL(1002,"message_type is null"),
    MESSAGETYPE_IS_NULL(1003,"message_body is null"),
    IS_SEQUENCE(1004,"is_sequence is null"),
    SYSTEM_ID_NULL(1005,"system_id can not be null!"),
    EMAIL_IS_NULL(1006,"email is null"),
    DINGDING_IS_NULL(1007,"dingding'type is null"),
    PHONENUMBER_IS_NULL(1008,"message'type is null"),
    SYSTEM_IS_NOT_EXIT(1009,"system is not exit!"),


    //validate
    MULTIPLICITY_MESSAGE(2000,"multiplicity message"),
    EMAIL_SEND_FAILD(2001,"email send fail"),
    RECEIVE_IS_NULL(2002,"receiver can not be null!"),


    //redis error
    STRING_REDIS_TEMPLATE_ERROR(3000,"StringRedisTemplate error"),
    STRING_REDIS_TEMPLATE_EMAIL_ERROR(3001,"StringRedisTemplate email error"),
    STRING_REDIS_TEMPLATE_DUPLICATE_EMAIL(3002,"StringRedisTemplate email duplicate"),


    UNKNOWN_ERROR(500,"UNNOW ERROR!")
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
