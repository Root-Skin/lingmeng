package com.lingmeng.base;

import java.util.HashMap;

public class RestReturn extends HashMap<String, Object> {
    private Integer code;
    private Object data;
    private Object message;

    public RestReturn() {
    }

    public RestReturn(Boolean success, Integer code, Object data, Object message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static RestReturn ok( ) {
        RestReturn result = new RestReturn();
        return result;
    }
    public static RestReturn ok(String message ) {
        RestReturn result = new RestReturn();
        result.put("code", 30000);
        result.put("message",message);
        result.put("data", null);
        return result;
    }
    public static RestReturn ok(Object data) {
        RestReturn result = new RestReturn();
        result.put("code", 10000);
        result.put("message", "操作成功");
        result.put("data", data);
        return result;
    }
    public static RestReturn ok(String message, Object data) {
        RestReturn result = new RestReturn();
        result.put("code", 10000);
        result.put("message", message);
        result.put("data", data);
        return result;
    }


    public static RestReturn error() {
        RestReturn result = new RestReturn();
        result.put("code", 30000);
        result.put("data", null);
        result.put("message", "操作失败");
        return result;
    }

    public static RestReturn error(Object data) {
        RestReturn result = new RestReturn();
        result.put("code", 30000);
        result.put("data", data);
        result.put("message", "操作失败");
        return result;
    }
    public static  RestReturn error(Object data, String message) {
        RestReturn result = new RestReturn();
        result.put("code", 30000);
        result.put("data", data);
        result.put("message", message);
        return result;
    }
}
