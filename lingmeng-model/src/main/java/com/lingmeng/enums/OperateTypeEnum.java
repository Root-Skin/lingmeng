package com.lingmeng.enums;

 /**
  * @Author skin
  * @Date  2021/1/7
  * @Description 操作类型
  **/
public enum OperateTypeEnum {

    QUERY(0, "查询"),

    EXPORT(1, "导出");

    int code;

    String msg;

     OperateTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
