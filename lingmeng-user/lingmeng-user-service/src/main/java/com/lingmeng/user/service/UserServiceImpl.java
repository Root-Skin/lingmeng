package com.lingmeng.user.service;

import cache.CacheKey;
import cache.MD5Utils;
import com.lingmeng.base.RestReturn;
import com.lingmeng.user.api.UserService;
import com.lingmeng.user.mapper.UserMapper;
import com.lingmeng.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public Boolean sendEmailCode(String receiver) {
        try {
            amqpTemplate.convertAndSend("lingmeng.email.exchange", "email.verify.code", receiver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("邮箱验证码发送失败.email:{}", receiver);
            return false;
        }

    }


    @Override
    public Boolean register(User user, String code) {
        String codeCache = redisTemplate.opsForValue().get(CacheKey.EMAIL_CODE + user.getEmail());
        if (!code.equals(codeCache)) {
            return false;
        }
        //得到盐
        String salt = MD5Utils.generate(user.getPassword());
        //设置加盐后的密码

        user.setPassword(MD5Utils.MD5(salt));

        boolean result = userMapper.insert(user) == 1;
        if (result) {
            try {
                redisTemplate.delete(CacheKey.EMAIL_CODE + user.getEmail());
            } catch (Exception e) {
                logger.error("删除缓存验证码失败,code:{}", code, e);
            }
        }
        return result;
    }

    @Override
    public RestReturn query(String userName, String password) {
        User user = userMapper.queryUser(userName, password);
        return RestReturn.ok(user);
    }

    @Override
    public Boolean registerCheck(String data, String type) {
        User user = new User();
        switch (type) {
            case "1":
                user.setUserName(data);
                break;
            case "2":
                user.setPhone(data);
                break;
            case "3":
                user.setEmail(data);
                break;
            default:
                return null;
        }
        return userMapper.selectCount(user)>0;
    }


}
