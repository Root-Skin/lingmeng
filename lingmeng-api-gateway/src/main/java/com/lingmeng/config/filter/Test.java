package com.lingmeng.config.filter;

import com.lingmeng.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author skin
 * @createTime 2021年02月26日
 * @Description
 */

@Controller
@RequestMapping()
public class Test {

    @RequestMapping("/test1")
    @ResponseBody
    public User test1(String id) {
        System.out.println("我是controller");
        User user = null;
        return user;
    }
}
