package com.lingmeng.client;

import com.lingmeng.base.RestReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "lingmeng-user-service")
public interface UserClient {
    /**
     * @Author skin
     * @Date 2020/9/3
     * @Description 获取验证码
     * receiver 表示接受地址
     **/
    @GetMapping("/user/query")
    RestReturn queryUser(@RequestParam String userName, @RequestParam String password);

}
