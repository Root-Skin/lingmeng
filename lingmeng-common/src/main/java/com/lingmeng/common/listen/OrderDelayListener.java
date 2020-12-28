package com.lingmeng.common.listen;


import com.alibaba.fastjson.JSON;
import com.lingmeng.common.config.MqConf;
import com.lingmeng.dao.goods.SkuMapper;
import com.lingmeng.goods.model.Sku;
import com.lingmeng.miaosha.model.MiaoshaOrder;
import com.lingmeng.miaosha.vo.MiaoshaQueueVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class OrderDelayListener {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuMapper skuMapper;

//    @RabbitHandler
//    @RabbitListener(queues = MqConf.LINGMENG_QUEUE)
//    void mqReceive(Message message, com.rabbitmq.client.Channel channel) throws IOException {
//        log.info("delay_5s队列消费开始");
//
//        String content = new String(message.getBody());
//        MiaoshaQueueVo miaoshaQueueVo = JSON.parseObject(content, MiaoshaQueueVo.class);
//        System.out.println(miaoshaQueueVo);
//        //如果不进行ack,查看是否进入死信
////        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
////        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
////        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//        log.info("delay_5s队列消费结束");
//    }
    @RabbitHandler
    @RabbitListener(queues = MqConf.LINGMENG_DEAD_QUEUE)
    void mqDeadReceive(Message message, com.rabbitmq.client.Channel channel) throws IOException {
        log.info("死信开始消费");
        System.out.println("repeatTradeQueue 接收时间:"+ LocalDateTime.now().toString()+" 接收内容:");
        String content = new String(message.getBody());
        MiaoshaQueueVo miaoshaQueueVo = JSON.parseObject(content, MiaoshaQueueVo.class);
        System.out.println("miaoshaQueueVo");
        //如果不进行ack,查看是否进入死信
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("死信开始消费结束");
    }

    public void rollbackOrder(MiaoshaQueueVo miaoshaQueueVo){
        String userName = miaoshaQueueVo.getUserName();

        MiaoshaOrder miaoshaOrder = (MiaoshaOrder)redisTemplate.boundHashOps("miaoshaOrder").get(userName);
        //redis
        if(miaoshaOrder!=null){
            //关闭支付
            //删除订单
            redisTemplate.boundHashOps("miaoshaOrder").delete(userName);
            //查看redis中是否含有该商品的数据
            Sku sku = (Sku)this.redisTemplate.boundHashOps("miaoshaGood_" + miaoshaQueueVo.getTime()).get(miaoshaQueueVo.getSkuId());
            //数据库中加载
            if(sku==null){
                sku = skuMapper.selectById(miaoshaQueueVo.getSkuId());
            }
            //将数据加①
            Long miaoshaGoodCount = redisTemplate.boundHashOps("miaoshaGoodCount").increment(miaoshaQueueVo.getSkuId(), 1);
            sku.setStock(miaoshaGoodCount.intValue());
            redisTemplate.boundListOps("miaoshaGoodCountList_"+miaoshaQueueVo.getSkuId()).leftPush(miaoshaQueueVo.getSkuId());

            redisTemplate.boundHashOps("miaoshaGood_"+miaoshaQueueVo.getTime()).put(miaoshaQueueVo.getSkuId(),sku);

            //清理排队标识
            redisTemplate.boundHashOps("UserQueueCount").delete(miaoshaQueueVo.getUserName());
            //清理抢单标识
            redisTemplate.boundHashOps("currentUserGrabbingStatus").delete(miaoshaQueueVo.getUserName());

        }
    }

}
