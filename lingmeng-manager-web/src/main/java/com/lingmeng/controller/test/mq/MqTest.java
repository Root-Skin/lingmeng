package com.lingmeng.controller.test.mq;

import com.lingmeng.base.RestReturn;
import com.lingmeng.common.config.MqConf;
import com.lingmeng.common.constant.MqConstant;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author skin
 * @createTime 2021年02月25日
 * @Description
 *  测试使用mq异步发送卡券(异步)
 */
@RestController
public class MqTest implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }


    @RequestMapping("/MqTest")
    public RestReturn sendCard() {
        String cardName = "tt";

        Message message = MessageBuilder.withBody(SerializationUtils.serialize(cardName)).andProperties(MessagePropertiesBuilder.newInstance().setContentType(MqConstant.CONTENT_TYPE)
                .setMessageId(UUID.randomUUID().toString().substring(0, 15))
                .setReceivedExchange(MqConstant.LINGMENG_CARD_QUEUE_EXCHANGE)
                .setReceivedRoutingKey(MqConstant.LINGMENG_CARD_ROUTING_KEY)
                .build()).build();
        //发送消息
        rabbitTemplate.convertAndSend(MqConstant.LINGMENG_CARD_QUEUE_EXCHANGE,MqConstant.LINGMENG_CARD_ROUTING_KEY, message,
                new CorrelationData(message.getMessageProperties().getMessageId()));

        return RestReturn.ok("发送消息成功--等待异步消费");
    }


    @RequestMapping("/MqTestStatus")
    public RestReturn changeStatus() {

        String id  = "00004ccf2bf91a05b251f1109172ba8b";

        Message message = MessageBuilder.withBody(SerializationUtils.serialize(id)).andProperties(MessagePropertiesBuilder.newInstance().setContentType(MqConstant.CONTENT_TYPE)
                .setMessageId(UUID.randomUUID().toString().substring(0, 15))
                .setReceivedExchange(MqConstant.LINGMENG_STATUS_QUEUE_EXCHANGE)
                .setReceivedRoutingKey(MqConstant.LINGMENG_STATUS_ROUTING_KEY)
                .build()).build();
        //发送消息
        rabbitTemplate.convertAndSend(MqConstant.LINGMENG_STATUS_QUEUE_EXCHANGE,MqConstant.LINGMENG_STATUS_ROUTING_KEY, message,
                new CorrelationData(message.getMessageProperties().getMessageId()));

        return RestReturn.ok("异步修改状态第一步");
    }


    /**
     * @author skin
     * @param
     * @Date  2021/4/23 13:59
     * @description 测试消息丢失的情况
     **/
    @RequestMapping("/MqLostTest")
    public RestReturn lost() {

        for(int i=2;i<5;i++){
            try {
                Message message = MessageBuilder.withBody(SerializationUtils.serialize(i)).andProperties(MessagePropertiesBuilder.newInstance().setContentType(MqConstant.CONTENT_TYPE)
                        .setMessageId(UUID.randomUUID().toString().substring(0, 15))
                        .setReceivedExchange(MqConstant.LOST_QUEUE_EXCHANGE)
                        .setReceivedRoutingKey(MqConstant.LOST_ROUTING_KEY)
                        .build()).build();
                //发送消息
                rabbitTemplate.convertAndSend(MqConstant.LOST_QUEUE_EXCHANGE,MqConstant.LOST_ROUTING_KEY, message,
                        new CorrelationData(message.getMessageProperties().getMessageId()));

//            int i = 1/0;
            } catch (AmqpException e) {
                e.printStackTrace();
            }
        }
        return RestReturn.ok("发送消息成功--等待异步消费");
    }


    /**
     * @author skin
     * @param
     * @Date  2021/4/24 15:26
     * @description 死信队列的方式进行测试
      **/
    @RequestMapping("/MqLostTest2")
    public RestReturn lost2() {

        for(int i=2;i<5;i++){
            try {
                Message message = MessageBuilder.withBody(SerializationUtils.serialize(i)).andProperties(MessagePropertiesBuilder.newInstance().setContentType(MqConstant.CONTENT_TYPE)
                        .setMessageId(UUID.randomUUID().toString().substring(0, 15))
                        .setReceivedExchange(MqConf.LINGMENG_EXCHANGE)
                        .setReceivedRoutingKey(MqConf.LINGMENG_QUEUE_ROUTE_KEY)
                        .build()).build();
                //发送消息
                rabbitTemplate.convertAndSend(MqConf.LINGMENG_EXCHANGE,MqConf.LINGMENG_QUEUE_ROUTE_KEY, message,
                        new CorrelationData(message.getMessageProperties().getMessageId()));

//            int i = 1/0;
            } catch (AmqpException e) {
                e.printStackTrace();
            }
        }
        return RestReturn.ok("发送消息成功--等待异步消费");
    }







    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("ack send succeed: " + correlationData);
        } else {
            System.out.println("ack send failed: " + correlationData + "|" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("ack " + message + " 发送失败");
    }
}
