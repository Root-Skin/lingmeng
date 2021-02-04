package com.lingmeng.testEnum;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
public enum ttEnum {

    ONLINE(0, "在线面诊");

    int code;
    String message;

    ttEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
