package com.lingmeng.common.config.qiniu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author skin
 * @Date  2021/1/27 20:12
 * @description 七牛注解
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Qiniu {

}
