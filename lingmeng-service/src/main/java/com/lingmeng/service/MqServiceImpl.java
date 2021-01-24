package com.lingmeng.service;

import com.alibaba.fastjson.JSON;
import com.lingmeng.api.mq.MqService;
import com.lingmeng.miaosha.vo.MiaoshaQueueVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendOrderDelayMessage(MiaoshaQueueVo miaoshaQueueVo) {
        try {
            System.out.println("DeadLetterSender 发送时间:"+ LocalDateTime.now().toString()+" msg内容：");

            amqpTemplate.convertAndSend("lingmeng_order_queue", JSON.toJSONString(miaoshaQueueVo));
        } catch (AmqpException e) {
            log.error("订单延时队列消息异常", e);
        }
    }
    /**
     * @author skin
     * @param orderId
     * @Date  2021/1/15 15:23
     * @description 普通订单,延时处理(5s检查状态)
     **/
    @Override
    public void normalOrderDelayMessage(String orderId) {
        amqpTemplate.convertAndSend("normal_lingmeng_order_queue", orderId);
    }
}
