package com.lingmeng.api;


import com.lingmeng.base.RestReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface MailService {

     /**
     * @Author skin
     * @Date  2020/9/3
     * @Description 获取验证码
      * receiver 表示接受地址
     **/
     @GetMapping(value = "lingmeng-sms-service/sms/getCode")
    RestReturn getCode(@RequestParam String receiver);
}
