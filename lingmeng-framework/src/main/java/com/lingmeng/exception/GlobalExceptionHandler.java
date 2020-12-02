package com.lingmeng.exception;

import com.google.common.collect.ImmutableMap;
import com.lingmeng.base.RestReturn;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static ImmutableMap<Class<? extends Throwable>,RestException> EXCEPTIONS;

    protected static ImmutableMap.Builder<Class<? extends Throwable>,RestException> builder = ImmutableMap.builder();

    @ResponseBody
    @ExceptionHandler(value = RestException.class)
    public RestReturn globalExceptionHandler(RestException e) {
        if (e.getInfo() != null) {
            log.error("业务异常", e);
            return RestReturn.error(e.getCode(), e.getInfo().getMessage());
        } else {
            log.error("业务异常", e);
        }
        return RestReturn.error(e.getCode(), e.getMessage());
    }


    }

