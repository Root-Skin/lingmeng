package com.lingmeng.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author skin
 * @Date 2021/1/4
 * @Description 记录接口的执行时间
 **/
@Aspect
@Component
@Slf4j
public class ApiTimeAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiTimeAspect.class);


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Author skin
     * @Date 2021/1/4
     * @Description 设置切点
     **/
    @Pointcut("execution(public * com.lingmeng.controller..*(..))")
    public void apiTimeAspect() {
    }

    @Around("apiTimeAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {


        log.info("====== 开始执行 {}.{} ======",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        // 记录开始时间
        long begin = System.currentTimeMillis();

        // 执行目标 service
        Object result = joinPoint.proceed();

        // 记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else if (takeTime > 2000) {
            log.warn("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else {
            log.info("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        }

        return result;
    }
}
