package com.lingmeng.service.auth;


import com.alibaba.fastjson.JSON;
import com.lingmeng.api.auth.AuthService;
import com.lingmeng.api.user.UserService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.common.config.JwtProperties;
import com.lingmeng.common.utils.auth.JwtUtils;
import com.lingmeng.common.utils.auth.UserInfo;
import com.lingmeng.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtProperties properties;

    @Autowired
    private UserService userService;


    public String authenticate(String userName,String password){
        RestReturn restReturn = userService.query(userName, password);
        User user = JSON.parseObject(JSON.toJSONString(restReturn.get("data")), User.class);
        if(user == null){
            return null;
        }
        try {
            String token = JwtUtils.generateToken(new UserInfo(user.getId(),user.getUserName()),properties.getPrivateKey(),properties.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
