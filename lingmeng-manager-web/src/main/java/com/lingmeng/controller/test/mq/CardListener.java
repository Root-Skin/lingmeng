package com.lingmeng.controller.test.mq;


import com.lingmeng.common.constant.MqConstant;
import com.lingmeng.common.listen.SmsListener;
import com.lingmeng.dao.mq.CardMapper;
import com.lingmeng.mq.model.Card;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
  * @Author skin
  * @Date  2020/9/4
  * @Description  发送邮件的监听器
  **/
@Component
public class CardListener {



    private  static  final Logger logger = LoggerFactory.getLogger(SmsListener.class);

    @Autowired
    private CardMapper cardMapper;

    @RabbitHandler
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = MqConstant.LINGMENG_CARD_QUEUE), exchange = @Exchange(value = MqConstant.LINGMENG_CARD_QUEUE_EXCHANGE), key = MqConstant.LINGMENG_CARD_ROUTING_KEY)})
    public void listenEmail(final Message msg, final Channel channel){
        final String message = new String(msg.getBody());
        //没有监听到消息
        if(msg ==null ){
            return;
        }
        logger.info("开始消费卡卷消息");
        //发送验证码
        try {
            for(int i=0;i<10000;i++){
                Card newCard = new Card();
                newCard.setCardName("tt"+i);
                cardMapper.insert(newCard);
            }
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = MqConstant.LINGMENG_STATUS_QUEUE), exchange = @Exchange(value = MqConstant.LINGMENG_STATUS_QUEUE_EXCHANGE), key = MqConstant.LINGMENG_STATUS_ROUTING_KEY)})
    public void listenStatus(final Message msg, final Channel channel){
        System.out.println(msg.getBody());
        final String message = new String(msg.getBody());
        //没有监听到消息
        if(msg ==null ){
            return;
        }
        logger.info("开始消费卡卷消息");
        //发送验证码
        try {
            System.out.println(message);
            Card card = cardMapper.selectById(message);

            //顺序执行
            if(card.getCardName().equals("tt1")){
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
}
