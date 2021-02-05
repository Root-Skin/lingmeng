package com.lingmeng.script;

import java.lang.reflect.Method;

 /**
  * @Author skin
  * @Date  2021/1/10
  * @Description  前置后置处理器,以及设置下一个节点
  **/
public interface ExecutedPostProcessor {

     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description 前置处理
      **/
    Method postProcessBeforeInitialization(Method method, Class c);

     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description 后置处理
      **/
    Method postProcessAfterInitialization(Method method, Class c);

     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description 设置节点
      **/
    void setNextExecutedPostProcessor(ExecutedPostProcessor nextExecutedPostProcessor);

}
