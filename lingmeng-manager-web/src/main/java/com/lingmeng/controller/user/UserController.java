package com.lingmeng.controller.user;

import com.lingmeng.base.RestReturn;
import com.lingmeng.api.user.UserService;
import com.lingmeng.user.vo.req.UserReq;
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

        //TT开的的第一行  代码

        //TT在本地新增了代码,没有提交到远程,并且在原来的代码上进行了修改

        //xx今天晚上要学习英语听力,英语口语 ,并且在(合并之后涛涛删除了一些数据)上(涛涛发现了XX的一个BUG进行了修改)添加了一个空格
        // .代码合并后推到远程

        //合并了XX的代码之后,我并没有马上推上远程继续进行了开发(XX心情不高兴 ,想故意产生冲突)


        //在没有拉取TT合并代码的情况下,XX进行了新的代码的书写()没有修改旧的代码

        //XX故意制造冲突



        return RestReturn.ok("注册成功");
    }

}
