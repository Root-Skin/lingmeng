package com.lingmeng.controller.test.mq;

import com.lingmeng.base.RestReturn;
import com.lingmeng.common.constant.MqConstant;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author skin
 * @createTime 2021年02月25日
 * @Description
 *  测试使用mq异步发送卡券(异步)
 */
@RestController
public class MqTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

}
