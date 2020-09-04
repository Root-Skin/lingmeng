package com.lingmeng.user.controller;

import com.lingmeng.base.RestReturn;
import com.lingmeng.user.api.UserService;
import com.lingmeng.user.client.SmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SmsClient smsClient;

    @RequestMapping("/register")
    public RestReturn registerUser(@RequestParam String receiver) {
        return smsClient.getCode(receiver);
    }
}
