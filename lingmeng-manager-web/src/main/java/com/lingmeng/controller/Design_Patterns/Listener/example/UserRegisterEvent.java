package com.lingmeng.controller.Design_Patterns.Listener.example;

import org.springframework.context.ApplicationEvent;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
public class UserRegisterEvent  extends ApplicationEvent {


    public UserRegisterEvent(Object source) {
        super(source);
    }
}
