package com.banma.hermes.producer.handle;

import com.banma.hermes.producer.aspect.HttpAspect;
import com.banma.hermes.producer.exception.ProducerException;
import com.banma.hermes.producer.domain.ResponseData;
import com.banma.hermes.producer.utils.ResultUtil;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by banma on 2017/12/4.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData handel(Exception e){
        if (e instanceof ProducerException){
            ProducerException producerException = (ProducerException) e;
            return ResultUtil.error(producerException.getCode(),producerException.getMessage());
        }else {
            logger.info("System Error:",e);
            return ResultUtil.error(500,e.getMessage());
        }
    }
}
