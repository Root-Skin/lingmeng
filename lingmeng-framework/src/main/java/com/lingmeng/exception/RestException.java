package com.lingmeng.exception;

import lombok.Data;

 /**
  * @Author skin
  * @Date  2020/9/10
  * @Description 自定义异常类
  **/
@Data
public class RestException  extends  RuntimeException{

    private int code = 99999;

    private String message;

    private Exception info;

    public RestException(int code, String message, Exception info) {
        this.message = message;
        this.info = info;
    }
    public RestException( String message, Exception info) {
        this.message = message;
        this.info = info;
    }
    public RestException( String message) {
        this.message = message;
    }
}
