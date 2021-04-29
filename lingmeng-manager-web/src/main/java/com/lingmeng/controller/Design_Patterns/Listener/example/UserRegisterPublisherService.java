package com.lingmeng.controller.Design_Patterns.Listener.example;

import com.alibaba.fastjson.JSON;
import com.lingmeng.user.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
@Service
public class UserRegisterPublisherService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void insert(User user){
        UserRegisterEvent event = new UserRegisterEvent(JSON.toJSONString(user));
        applicationEventPublisher.publishEvent(event);
        System.out.println("插入数据库");
    }
}
