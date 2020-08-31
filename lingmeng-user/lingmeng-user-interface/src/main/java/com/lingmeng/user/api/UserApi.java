package com.lingmeng.user.api;

import com.lingmeng.base.RestReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public interface UserApi {

    @GetMapping("lingmeng-user-service/user/query")
    public RestReturn queryUser(@RequestParam String userName, @RequestParam String password);
}
