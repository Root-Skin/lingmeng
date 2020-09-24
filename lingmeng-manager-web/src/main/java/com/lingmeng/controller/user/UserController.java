package com.lingmeng.controller.user;

import com.lingmeng.base.RestReturn;
import com.lingmeng.api.user.UserService;
import com.lingmeng.model.user.vo.req.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Author skin
     * @Date 2020/9/9
     * @Description 查询用户
     **/
    @RequestMapping("/com/lingmeng/user/query")
    public RestReturn sendEmailCode(@RequestParam String userName, @RequestParam String password) {
        return userService.query(userName, password);
    }

    /**
     * @Author skin
     * @Date 2020/9/6
     * @Description 发送邮箱验证码
     **/
    @RequestMapping("/sendEmailCode")
    public RestReturn sendEmailCode(@RequestParam String receiver) {
        Boolean b = userService.sendEmailCode(receiver);
        if (b == null || !b) {
            return RestReturn.error("邮箱验证码发送失败");
        }
        return RestReturn.ok("邮箱验证码发送成功");
    }

    /**
     * @Author skin
     * @Date 2020/9/9
     * @Description 用户注册参数校验(用户名, 手机, 邮箱)  1.用户名字,2.手机号 3.邮箱
     **/
    @GetMapping("/registerCheck")
    public RestReturn registerCheck(@RequestParam String data, @RequestParam(defaultValue = "1") String type) {
        Boolean result = userService.registerCheck(data, type);
        if (result != null && result == false) {
            return RestReturn.ok(false, "校验成功,该数据不存在");
        }
        return RestReturn.error(true, "已经存在");
    }


    /**
     * @Author skin
     * @Date 2020/9/6
     * @Description 用户注册
     **/
    @RequestMapping("/register")
    public RestReturn register(@RequestBody @Valid UserReq userReq) {
        Boolean result = userService.register(userReq);
        if (result == null || !result) {
            return RestReturn.error("注册失败");
        }
        return RestReturn.ok("注册成功");
    }

}
