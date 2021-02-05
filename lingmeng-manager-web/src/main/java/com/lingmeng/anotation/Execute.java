package com.lingmeng.anotation;

import java.lang.annotation.*;

 /**
  * @Author skin
  * @Date  2021/1/10
  * @Description 项目启动自动执行
  **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Execute {




     String time() default "";

     String[] params() default {};
    /**
     * 执行时间范围
     * @return
     */
    String[] timeArray() default {};
}
