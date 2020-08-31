package com.lingmeng.base.RestReturnEnum;

public enum RestReturnEnum {

    SUCCESS(10000,"成功"),

    ERROR(20000,"失败");

    private Integer code;

    private String message;

    RestReturnEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
