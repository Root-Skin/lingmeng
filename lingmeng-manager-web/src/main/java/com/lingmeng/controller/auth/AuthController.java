package com.lingmeng.controller.auth;


import com.lingmeng.api.auth.AuthService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.common.config.JwtProperties;
import com.lingmeng.common.utils.auth.CookieUtils;
import com.lingmeng.common.utils.auth.JwtUtils;
import com.lingmeng.common.utils.auth.UserInfo;
import com.lingmeng.user.model.UserAuthentic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
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
            @RequestBody UserAuthentic userAuthentic,
            HttpServletRequest request,
            HttpServletResponse response) {

        //登陆校验
        String token = this.authService.authenticate(userAuthentic.getUserName(), userAuthentic.getPassword());
        if (StringUtils.isEmpty(token)) {
            return RestReturn.error();
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, prop.getCookieName(),
                token, prop.getCookieMaxAge(), true);
        return RestReturn.ok((Object) token);

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

      /**
       * @Author skin
       * @Date  2020/11/14
       * @Description 十次方页面跳转
       **/
    @GetMapping("/verify2")
    public RestReturn verify2( String token,HttpServletRequest request,HttpServletResponse response){
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
