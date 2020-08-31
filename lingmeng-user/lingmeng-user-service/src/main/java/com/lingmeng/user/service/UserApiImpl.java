package com.lingmeng.user.service;

import com.lingmeng.base.RestReturn;
import com.lingmeng.user.api.UserApi;
import com.lingmeng.user.mapper.UserMapper;
import com.lingmeng.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApiImpl implements UserApi {

    @Autowired
    private UserMapper userMapper;


    @Override
    public RestReturn queryUser(String userName, String password) {
        User user = userMapper.queryUser(userName, password);
        return RestReturn.ok(user);
    }
}
