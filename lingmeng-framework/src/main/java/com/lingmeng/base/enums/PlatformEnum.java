package com.lingmeng.base.enums;

/**
 * 平台枚举
 * @author shililu
 * @since 2018-11-15
 *
 */
public enum PlatformEnum {

    /**
     * 安卓
     */
    ANDROID(0, "android"),
    IOS(1, "ios"),
    RN(2, "rn"),
    web(3, "web");

    int code;
    String message;

    PlatformEnum(int code, String message) {
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
            for (PlatformEnum s : PlatformEnum.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
