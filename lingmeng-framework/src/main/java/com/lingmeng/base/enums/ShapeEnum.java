package com.lingmeng.base.enums;

/**
 * 消息形式
 * @author shililu
 * @since 2018-11-15
 */
public enum ShapeEnum {

    MSG(0, "消息"),
    TASK(1, "one-day"),
    TASKED(2, "已完成-one-day");

    int code;
    String message;

    ShapeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (ShapeEnum s : ShapeEnum.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
