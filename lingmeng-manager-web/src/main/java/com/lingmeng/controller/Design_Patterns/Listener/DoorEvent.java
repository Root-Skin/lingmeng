package com.lingmeng.controller.Design_Patterns.Listener;

import lombok.Builder;
import lombok.Data;

import java.util.EventObject;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description  开关门的事件
 */
@Data
@Builder
public class DoorEvent extends EventObject {

    private Integer doorStatus;


    public DoorEvent(Object source) {
        super(source);
    }
    public DoorEvent(Object source, Integer status) {
        super(source);
        this.doorStatus = status;
    }

    public void setStatus(Integer status) {
        this.doorStatus = status;
    }

    public Integer getStatus() {
        return doorStatus;
    }

}