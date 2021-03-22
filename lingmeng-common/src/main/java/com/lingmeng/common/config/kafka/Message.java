package com.lingmeng.common.config.kafka;

import lombok.Data;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description
 */
@Data
public class Message {
    private int count ; 		//消息的次数
    private Long timeStamp ; 	//消息的时间
    private String message ; 	//消息体

    @Override
    public String toString() {
        return "Message{" +
                "count=" + count +
                ", timeStamp=" + timeStamp +
                ", message='" + message + '\'' +
                '}';
    }
}
