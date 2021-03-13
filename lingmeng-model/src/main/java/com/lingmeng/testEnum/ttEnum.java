package com.lingmeng.testEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
public enum ttEnum  {

    ONLINE(0, "在线面诊");


    @EnumValue
    private final int code;
    private final String message;

    ttEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }


}
