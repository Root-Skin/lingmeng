package com.lingmeng.script;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author: yebin
 * @create: 2019-12-06 14:39
 * @description: 默认头前置处理器处理器
 **/
@Slf4j
public class DefaultExecutedPostProcessor implements ExecutedPostProcessor {

    //继承接口,并把当前接口当作变量
    private ExecutedPostProcessor nextExecutedPostProcessor;


    @Override
    public Method postProcessBeforeInitialization(Method method,Class c) {

        log.info("开始执行默认的执行器前置处理 方法名称为=====[ "+c.getName()+" : "+method.getName()+" ]");
        if(nextExecutedPostProcessor != null){
            return nextExecutedPostProcessor.postProcessBeforeInitialization(method,c);
        }
        return method;
    }
    @Override
    public Method postProcessAfterInitialization(Method method,Class c) {
      
        log.info("开始执行默认的执行器后置处理 方法名称为=====[ "+c.getName()+" : "+method.getName()+" ]");
        if(nextExecutedPostProcessor != null){
            return nextExecutedPostProcessor.postProcessAfterInitialization(method,c);
        }
        return method;
    }

    @Override
    public void setNextExecutedPostProcessor(ExecutedPostProcessor nextExecutedPostProcessor) {
        this.nextExecutedPostProcessor = nextExecutedPostProcessor;
    }
}
