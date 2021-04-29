package com.lingmeng.controller.test.mq;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;
import com.lingmeng.common.config.MqConf;
import com.lingmeng.common.constant.MqConstant;
import com.lingmeng.common.listen.SmsListener;
import com.lingmeng.dao.mq.CardMapper;
import com.lingmeng.mq.model.Card;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author skin
 * @Date 2020/9/4
 * @Description 发送邮件的监听器
 **/
@Component
@Slf4j
public class CardListener {


    private static final Logger logger = LoggerFactory.getLogger(SmsListener.class);

    @Autowired
    private CardMapper cardMapper;

    @RabbitHandler
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = MqConstant.LINGMENG_CARD_QUEUE), exchange = @Exchange(value = MqConstant.LINGMENG_CARD_QUEUE_EXCHANGE), key = MqConstant.LINGMENG_CARD_ROUTING_KEY)})
    public void listenEmail(final Message msg, final Channel channel) {
        final String message = new String(msg.getBody());
        //没有监听到消息
        if (msg == null) {
            return;
        }
        logger.info("开始消费卡卷消息");
        //发送验证码
        try {
            for (int i = 0; i < 10000; i++) {
                Card newCard = new Card();
                newCard.setCardName("tt" + i);
                cardMapper.insert(newCard);
            }
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = MqConstant.LINGMENG_STATUS_QUEUE), exchange = @Exchange(value = MqConstant.LINGMENG_STATUS_QUEUE_EXCHANGE), key = MqConstant.LINGMENG_STATUS_ROUTING_KEY)})
    public void listenStatus(final Message msg, final Channel channel) {
        System.out.println(msg.getBody());
        final String message = new String(msg.getBody());
        //没有监听到消息
        if (msg == null) {
            return;
        }
        logger.info("开始消费卡卷消息");
        //发送验证码
        try {
            System.out.println(message);
            Card card = cardMapper.selectById(message);

            //顺序执行
            if (card.getCardName().equals("tt1")) {
                card.setCardName("tt2");
                channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            card.setCardName("tt1");
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param msg
     * @param channel
     * @author skin
     * @Date 2021/4/23 14:02
     * @description 测试消息丢失
     **/
    @RabbitHandler
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = MqConstant.LOST_QUEUE), exchange = @Exchange(value = MqConstant.LOST_QUEUE_EXCHANGE), key = MqConstant.LOST_ROUTING_KEY)})
    public void lost(final Message msg, final Channel channel) throws IOException {

//        final String message = new String(msg.getBody(),"UTF-8");
        int num = (int) SerializationUtils.deserialize(msg.getBody());
//        final String message = new String(msg.getBody());
        //没有监听到消息

        try {
            if (num % 2 == 0) {
                logger.info("测试丢失开始消费");
                int i = 1 / 0;
            }
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {
            //重新发送到队列尾巴

            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);

            channel.basicPublish(msg.getMessageProperties().getReceivedExchange(),
                    msg.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                    JSON.toJSONBytes(new Object()));
        }
    }

    /**
     * @author skin
     * @param msg
     * @param channel
     * @Date  2021/4/24 15:31
     * @description 死信队列防止异常
     **/
    @RabbitHandler
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = MqConf.LINGMENG_QUEUE), exchange = @Exchange(value = MqConf.LINGMENG_EXCHANGE), key = MqConf.LINGMENG_QUEUE_ROUTE_KEY)})
    public void lost2(final Message msg, final Channel channel) throws IOException {
        int num = (int) SerializationUtils.deserialize(msg.getBody());
        try {
            if (num % 2 == 0) {
                logger.info("测试丢失开始消费");
                int i = 1 / 0;
            }
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //重新发送到队列尾巴
            log.error("消息消费发生异常，error msg:{}", e.getMessage(), e);
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
        }
    }



}
