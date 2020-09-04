package com.lingmeng.user.service;

import com.lingmeng.user.api.UserService;
import com.lingmeng.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

}
