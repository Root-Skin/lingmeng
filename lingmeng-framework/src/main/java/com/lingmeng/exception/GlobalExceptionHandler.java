package com.lingmeng.exception;

import com.lingmeng.base.RestReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = RestException.class)
    public RestReturn globalExceptionHandler(RestException e){
        if(e.getInfo() !=null){
            log.error("业务异常",e);
            return RestReturn.error(e.getCode(), e.getInfo().getMessage());
        }else{
            log.error("业务异常",e);
        }
        return RestReturn.error(e.getCode(), e.getMessage());
    }
}
