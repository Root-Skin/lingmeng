package com.lingmeng.base.enums;


 /**
  * @Author skin
  * @Date  2021/1/12
  * @Description 消息类型
  **/
public enum MsgStatusEnum {



    TREATMENT("treatment", 0, 4, "流水", "治疗", 0, "", 0);



    String key;
    int modular;
    int code;
    String modularStr;
    String message;
    int flag;
    int shape;
    String routeKey;


    MsgStatusEnum(String key, int modular, int code, String modularStr, String message, int flag, String routeKey, int shape) {
        this.key = key;
        this.modular = modular;
        this.code = code;
        this.modularStr = modularStr;
        this.message = message;
        this.flag = flag;
        this.routeKey = routeKey;
        this.shape = shape;
    }

    public int getModular() {
        return modular;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static String valueOf(Integer modular, Integer code) {
        if (modular == null) {
            return "";
        } else {
            for (MsgStatusEnum s : MsgStatusEnum.values()) {
                if (s.getModular() == modular && s.getCode() == code) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

    public static void main(String args[]) {

        System.out.println(MsgStatusEnum.valueOf(0, 4));
    }


}
