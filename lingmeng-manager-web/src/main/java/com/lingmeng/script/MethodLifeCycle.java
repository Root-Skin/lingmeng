package com.lingmeng.script;

import com.lingmeng.dao.log.SysExecutedMethodsMapper;

 /**
  * @Author skin
  * @Date  2021/1/10
  * @Description 方法执行生命周期
  **/
public interface MethodLifeCycle {

     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description 待执行
      **/
    SysExecutedMethods waiting(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods);

     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description 执行中
      **/
    void invoking(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods);

     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description 执行异常
      **/
    void exception(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods);

     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description 执行完成
      **/
    void over(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods);
}
