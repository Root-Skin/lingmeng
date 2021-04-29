package com.lingmeng.controller.Design_Patterns.Listener;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
public class OpenDoorListener implements DoorListener {
    @Override
    public void DoorEvent(DoorEvent doorEvent) {
        Integer openStatus = doorEvent.getStatus();
        if(1 == openStatus) {
            System.out.println("the door is open");
        }
    }
}
