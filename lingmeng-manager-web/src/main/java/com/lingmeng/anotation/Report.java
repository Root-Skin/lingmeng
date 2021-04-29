package com.lingmeng.anotation;


import com.lingmeng.enums.OperateTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 /**
  * @Author skin
  * @Date  2021/1/7
  * @Description 带参自定义注解
  **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Report {

    /**
     * 操作类型 查询 or 导出(默认查询)
     *
     * @return
     */
    OperateTypeEnum type() default OperateTypeEnum.QUERY;

    /**
     * 报表名称
     *
     * @return
     */
    String value() default "";

}
