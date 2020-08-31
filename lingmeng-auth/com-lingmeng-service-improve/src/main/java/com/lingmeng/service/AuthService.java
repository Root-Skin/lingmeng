package com.lingmeng.service;


import com.alibaba.fastjson.JSON;
import com.lingmeng.base.RestReturn;
import com.lingmeng.client.UserClient;
import com.lingmeng.configuration.JwtProperties;
import com.lingmeng.entity.UserInfo;
import com.lingmeng.user.pojo.User;
import com.lingmeng.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;


    public String authenticate(String userName,String password){
        RestReturn restReturn = userClient.queryUser(userName, password);
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
