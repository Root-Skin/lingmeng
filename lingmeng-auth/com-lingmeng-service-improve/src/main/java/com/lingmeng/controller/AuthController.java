package com.lingmeng.controller;


import com.lingmeng.base.RestReturn;
import com.lingmeng.configuration.JwtProperties;
import com.lingmeng.entity.UserInfo;
import com.lingmeng.service.AuthService;
import com.lingmeng.utils.CookieUtils;
import com.lingmeng.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties prop;

    /**
     * @Author skin
     * @Date 2020/8/28
     * @Description 登陆授权
     **/
    @PostMapping("/check")
    public RestReturn authentication(
            @RequestParam String userName,
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response) {

        //登陆校验
        String token = this.authService.authenticate(userName, password);
        if (StringUtils.isEmpty(token)) {
            return RestReturn.error();
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, prop.getCookieName(),
                token, prop.getCookieMaxAge(), true);
        return RestReturn.ok("成功将获取到的token写入cookie");

    }
     /**
      * @Author skin
      * @Date  2020/8/29
      * @Description 验证用户信息
      **/
     @GetMapping("/verify")
    public RestReturn verifyUser(@CookieValue("LM_TOKEN") String token,HttpServletRequest request,HttpServletResponse response){
         try {
             UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());

             token = JwtUtils.generateToken(userInfo,prop.getPrivateKey(),prop.getExpire());

             CookieUtils.setCookie(request,response,prop.getCookieName(),token,prop.getCookieMaxAge(),true);
             return RestReturn.ok(userInfo);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return RestReturn.error("验证失败");
     }
}
