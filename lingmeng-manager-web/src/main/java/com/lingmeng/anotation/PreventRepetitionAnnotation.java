package com.lingmeng.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 /**
  * @Author skin
  * @Date  2021/1/6
  * @Description 防止重复提交
  **/
@Retention(RetentionPolicy.RUNTIME) // 在运行时可以获取
@Target(value = {ElementType.METHOD, ElementType.TYPE}) // 作用到类，方法，接口上等
public @interface PreventRepetitionAnnotation {

}
