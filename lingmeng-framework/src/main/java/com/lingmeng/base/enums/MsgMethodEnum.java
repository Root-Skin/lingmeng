package com.lingmeng.base.enums;

/**
 * 消息操作类型
 * @author shililu
 * @since 2018-11-15
 */
public enum MsgMethodEnum {

    ADD(0, "添加"),
    REFER(1, "刷新"),
    UPDATE(2,"更新"),
    DEL(3,"删除");

    int code;
    String message;

    MsgMethodEnum(int code, String message) {
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
            for (MsgMethodEnum s : MsgMethodEnum.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
