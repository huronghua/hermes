package com.banma.hermes.consumer.exception;

import com.banma.hermes.consumer.Utils.ResultEnum;
import org.slf4j.LoggerFactory;

/**
 * Created by banma on 2017/12/4.
 */
public class ConsumerException extends RuntimeException{

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ConsumerException.class);

    private static final long serialVersionUID = 1L;

    private Integer code;

    public ConsumerException(ResultEnum resultEnum){
        super(" MSG: "+resultEnum.getMessage()+" CODE: "+ resultEnum.getCode());
        this.code = resultEnum.getCode();
    }

    public ConsumerException(Exception e){
        logger.error("UNKOWN_EXCEPTION:"+e.getMessage());
    }

    public ConsumerException(int errorCode,String errorMessage){
        logger.error("CODE:"+errorCode+"MSG:"+errorMessage);
    }

}
