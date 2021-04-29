package com.lingmeng.controller.Design_Patterns.Listener;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
public class Door  implements DoorListener{

    public static List<DoorListener> getAllListener() {
        List<DoorListener> list = Lists.newArrayList();
        list.add(new OpenDoorListener());
        list.add(new CloseDoorListener());
        return list;
    }

    @Override
    public void DoorEvent(DoorEvent doorEvent) {
        DoorEvent open = new DoorEvent("open", 1);
        List<DoorListener> listeners = getAllListener();
        for (DoorListener listener : listeners) {
            listener.DoorEvent(open);
        }
    }
}
