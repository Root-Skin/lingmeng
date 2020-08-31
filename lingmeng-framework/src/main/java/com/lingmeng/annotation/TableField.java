package com.lingmeng.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  * @Author skin
  * @Date  2020/8/29
  * @Description 自定义实体数据库映射接口
  **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableField {
    String value() default "";
}
