package com.lingmeng.base.lingmengPlug.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 /**
  * @Author skin
  * @Date  2020/9/9
  * @Description
  **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {

    FieldFill fill() default FieldFill.DEFAULT;


    enum FieldFill {

        DEFAULT,

        INSERT,

        UPDATE,

        INSERT_UPDATE;

    }
}
