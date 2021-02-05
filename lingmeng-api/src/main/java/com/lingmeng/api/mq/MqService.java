package com.lingmeng.api.mq;

import com.lingmeng.miaosha.vo.MiaoshaQueueVo;

public interface MqService {

    //发送抢单的状态到延时队列
    void sendOrderDelayMessage(MiaoshaQueueVo miaoshaQueueVo);


    //用户普通下单,发送信息到延时队列
    void normalOrderDelayMessage(String  orderId);
}
