package com.lingmeng.base.enums;


/**
 * 消息所属模块
 * @author shililu
 * @since 2018-11-15
 */
public enum MsgModularEnum {
    STREAM(0, "流水"),
    VISIT(1, "回访"),
    PREPARATION(2, "报备"),
    CUSTOMER(3, "客户"),
    PAYMENT(4, "缴费"),

    SECRETARY(5, "小秘书");

    int code;
    String message;

    MsgModularEnum(int code, String message) {
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
            for (MsgModularEnum s : MsgModularEnum.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

    public static void main(String args[]){
//        System.out.println(MsgModularEnum.values());

        for(MsgModularEnum e:MsgModularEnum.values()){
//            System.out.println(e.code);
//            System.out.println(e.message);
            for(MsgStatusEnum ee:MsgStatusEnum.values()){
                 if(ee.modular==e.code){
                     System.out.println(e.code);

                     System.out.println(ee.getMessage());
                 }
            }
        }
    }
}
