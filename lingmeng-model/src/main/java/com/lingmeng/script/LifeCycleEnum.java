package com.lingmeng.script;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum LifeCycleEnum {


    WAITING("待执行"),   //待执行
    INVOKING("执行中"),  //执行中
    EXCEPTION("执行异常"), //执行异常
    OVER("执行完成");   //执行完成

    String value;


    LifeCycleEnum(String value) {

        this.value = value;
    }

    @JsonValue
    public  String getValue() {
        return this.value;
    }

    public  static  Map getLifeCycleEnum() {
        Map result = new HashMap<>();
        for (LifeCycleEnum IndexModelEnum : LifeCycleEnum.values()) {
            result.put(IndexModelEnum.getValue(),IndexModelEnum);
        }


        //这里是xx写的第一行
        //这里是xx写的第一行
        //这里是xx写的第一行
        //这里是xx写的第一行
        //tt也在这里进行了第一次提交
        //tt也在这里进行了第一次提交


        //测试上面修改
        //  预演示环境修改bug
        // 了一个bug

        return result;
    }
}
