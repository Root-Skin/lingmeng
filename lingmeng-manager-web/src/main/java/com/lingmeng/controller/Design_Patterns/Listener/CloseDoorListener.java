package com.lingmeng.controller.Design_Patterns.Listener;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
public class CloseDoorListener implements DoorListener {

    @Override
    public void DoorEvent(DoorEvent doorEvent) {
        Integer openStatus =  doorEvent.getStatus();
        if(0 == openStatus) {
            System.out.println("the door is close");
        }
    }
}