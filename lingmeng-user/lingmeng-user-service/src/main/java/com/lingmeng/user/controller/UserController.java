package com.lingmeng.user.controller;

import com.lingmeng.base.RestReturn;
import com.lingmeng.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class UserController {
    @Autowired
    private UserService userService;

    public RestReturn checkUser(){
        return RestReturn.ok();
    }

}
