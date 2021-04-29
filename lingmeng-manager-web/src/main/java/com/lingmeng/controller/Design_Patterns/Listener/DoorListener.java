package com.lingmeng.controller.Design_Patterns.Listener;/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */

import java.util.EventListener;

/**
 * @author skin
 * @date 2021/4/3 17:12
 * @description:   抽象监听器
 */
public interface DoorListener extends EventListener {

    void DoorEvent(DoorEvent doorEvent);
}
