package com.lingmeng.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.base.RestReturn;
import com.lingmeng.exception.RestException;
import com.lingmeng.dao.user.UserMapper;
import com.lingmeng.api.user.UserService;
import com.lingmeng.model.user.model.User;
import com.lingmeng.model.user.vo.req.UserReq;
import com.lingmeng.common.utils.cache.CacheKey;
import com.lingmeng.common.utils.cache.MD5Utils;
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
    public Boolean register(UserReq user) {
        String codeCache = redisTemplate.opsForValue().get(CacheKey.EMAIL_CODE + user.getEmail());
        if (!user.getCode().equals(codeCache)) {
            throw  new RestException("注册失败,验证码已失效,请重新获取验证码");
        }
        //得到盐
        String salt = MD5Utils.generate(user.getPassword());
        //设置加盐后的密码
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(MD5Utils.MD5(salt));

        boolean result = userMapper.insert(newUser) == 1;
        if (result) {
            try {
                redisTemplate.delete(CacheKey.EMAIL_CODE + user.getEmail());
            } catch (Exception e) {
                logger.error("删除缓存验证码失败,code:{}", user.getCode(), e);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        switch (type) {
            case "1":
                user.setUserName(data);
                queryWrapper.eq("user_name", data);
                break;
            case "2":
                user.setPhone(data);
                queryWrapper.eq("phone", data);
                break;
            case "3":
                user.setEmail(data);
                queryWrapper.eq("email", data);
                break;
            default:
                return null;
        }
        return userMapper.selectCount(queryWrapper)>0;
    }


}
