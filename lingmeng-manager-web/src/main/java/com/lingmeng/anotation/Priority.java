package com.lingmeng.anotation;

import java.lang.annotation.*;
 /**
  * @Author skin
  * @Date  2021/1/11
  * @Description 方法执行优先级注解，优先级value越大，越先执行
  **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {

    int value() default 0;
}