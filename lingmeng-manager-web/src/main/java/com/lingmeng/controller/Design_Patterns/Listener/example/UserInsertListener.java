package com.lingmeng.controller.Design_Patterns.Listener.example;

import com.alibaba.fastjson.JSON;
import com.lingmeng.user.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
@Component
public class UserInsertListener implements ApplicationListener<UserRegisterEvent> {


    @Override
    public void onApplicationEvent(UserRegisterEvent userRegisterEvent) {
        String source = (String)userRegisterEvent.getSource();
        User user = JSON.parseObject(source, User.class);
        //insert db
    }
}
