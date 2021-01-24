package com.lingmeng.base.enums;

/**
 * 消息类型
 * @author shililu
 * @since 2018-11-15
 */
public enum MsgTypeEnum {

    USER_TYPE(0, "用户类消息"),
    PAY_TYPE(1, "支付类"),
    EVENT_TYPE(2, "事件类"),
    APPROVAL_TYPE(3, "审核类");

    int code;
    String message;

    MsgTypeEnum(int code, String message) {
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
            for (MsgTypeEnum s : MsgTypeEnum.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
