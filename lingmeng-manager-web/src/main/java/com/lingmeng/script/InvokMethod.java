package com.lingmeng.script;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;

 /**
  * @Author skin
  * @Date  2021/1/10
  * @Description 反射方法包装类
  **/
@Data
@Accessors(chain = true)
//  methodPriorityQueues.get("default").add(new InvokMethod().setKey(generateId(c, m)).setMethod(m).setOrder(order).setAClass(c));
public class InvokMethod implements Comparable<InvokMethod>{

    private Integer order;

    private String key;

    private Method method;

    private Class aClass;


     @Override
     public int compareTo(@NotNull InvokMethod o) {
         return o.getOrder() - this.order ;
     }

 }
