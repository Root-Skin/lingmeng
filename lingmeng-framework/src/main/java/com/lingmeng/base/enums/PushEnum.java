package com.lingmeng.base.enums;

/**
 * 推送方式
 * @author shililu
 * @since 2018-11-15
 */
public enum PushEnum {

    BROADCAST(0, "广播"),
    GROUP(1, "组推"),
    SINGLE(2, "单推");

    int code;
    String message;

    PushEnum(int code, String message) {
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
            for (PushEnum s : PushEnum.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
