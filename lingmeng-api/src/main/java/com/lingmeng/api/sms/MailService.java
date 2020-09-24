package com.lingmeng.api.sms;


import com.lingmeng.base.RestReturn;
import org.springframework.web.bind.annotation.RequestParam;



public interface MailService {

    /**
     * @Author skin
     * @Date 2020/9/3
     * @Description 获取验证码
     * receiver 表示接受地址
     **/
    RestReturn getCode(@RequestParam String receiver);
}
