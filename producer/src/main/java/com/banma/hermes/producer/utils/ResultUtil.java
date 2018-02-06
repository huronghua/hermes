package com.banma.hermes.producer.utils;

import com.banma.hermes.producer.domain.ResponseData;

/**
 * Created by banma on 2017/12/4.
 */
public class ResultUtil {

    public static ResponseData success(Object object){
        ResponseData responseData = new ResponseData();
        responseData.setCode(0);
        responseData.setMsg("成功");
        responseData.setData(object);
        return responseData;
    }

    public static ResponseData success(){
        return success(null);
    }

    public static ResponseData error(Integer code,String message){
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        responseData.setMsg(message);
        return responseData;
    }
}
