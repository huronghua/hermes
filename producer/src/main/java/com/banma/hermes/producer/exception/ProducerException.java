package com.banma.hermes.producer.exception;

import com.banma.hermes.producer.utils.ResultEnum;

/**
 * Created by banma on 2017/12/4.
 */
public class ProducerException extends RuntimeException{

    private Integer code;

    public ProducerException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

}
