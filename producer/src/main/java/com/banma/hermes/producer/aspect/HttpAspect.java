package com.banma.hermes.producer.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by banma on 2017/12/4.
 */
@Aspect
@Component
public class HttpAspect {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.banma.hermes.producer.controller.ProducerController.send(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("url={}",request.getRequestURI());

        //method
        logger.info("method={}",request.getMethod());

        //ip
        logger.info("ip={}",request.getRemoteAddr());

        //class_method
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"." + joinPoint.getSignature().getName());

        //Args
        Object obj[] = joinPoint.getArgs();
        logger.info("args[]=" + obj[0]);


    }


    @AfterReturning(returning = "object", pointcut = "log()")
    public void afterRetuning(Object object) {
        logger.info(object.toString());
    }
}
