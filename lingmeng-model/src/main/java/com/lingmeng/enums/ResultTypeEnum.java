package com.lingmeng.enums;

 /**
  * @Author skin
  * @Date  2021/1/7
  * @Description 操作结果
  **/
public enum ResultTypeEnum {

    SUCCESS(0, "成功"),

    FAIL(1, "失败"); //异常发生视为 失败

    private int code;

    private String desc;


     ResultTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
