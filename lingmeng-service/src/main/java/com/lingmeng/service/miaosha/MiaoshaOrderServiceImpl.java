package com.lingmeng.service.miaosha;


import com.alibaba.fastjson.JSON;
import com.lingmeng.api.miaosha.ImiaoshaOrderService;
import com.lingmeng.api.mq.MqService;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.dao.miaosha.MiaoshaOrderMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.miaosha.model.MiaoshaOrder;
import com.lingmeng.miaosha.vo.MiaoshaQueueVo;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Service
public class MiaoshaOrderServiceImpl implements ImiaoshaOrderService {



    private  static volatile MiaoshaRedisDataVo  miaoshaGoodsInRedis;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;


    @Autowired
    private SkuStockMapper skuStockMapper;
    @Autowired
    private MqService mqService;


    @Override
//    @Async  //use customize Thread pool
    public Boolean miaoshaCreateOrder() {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MiaoshaQueueVo miaoshaQueueVo = JSON.parseObject(redisTemplate.boundListOps("miaoshaQueue").rightPop().toString(), MiaoshaQueueVo.class);

        if (miaoshaQueueVo != null) {

            Object o = redisTemplate.boundListOps("miaoshaGoodCountList_" + miaoshaQueueVo.getSkuId()).rightPop();
            if (o == null) {
                //clean queue
                cleanQueue(miaoshaQueueVo);
                return false;
            }


            String time = miaoshaQueueVo.getTime();
            String skuId = miaoshaQueueVo.getSkuId();
            String userName = miaoshaQueueVo.getUserName();
             miaoshaGoodsInRedis = JSON.parseObject(this.redisTemplate.boundHashOps("miaoshaGood_" + time).get(skuId).toString(), MiaoshaRedisDataVo.class);
            if (miaoshaGoodsInRedis == null || miaoshaGoodsInRedis.getStock() == 0) {
                throw new RestException("This item is sold out");
            }
            MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
            miaoshaOrder.setUserName(userName);
            miaoshaOrder.setSkuId(skuId);
            miaoshaOrder.setPayMoney(miaoshaGoodsInRedis.getPrice());
            miaoshaOrder.setStatus(1);

            miaoshaOrderMapper.insert(miaoshaOrder);

            //update status  and refresh redis status
            miaoshaQueueVo.setMiaoshaStatus(2);
            miaoshaQueueVo.setOrderId(miaoshaOrder.getId());
            miaoshaQueueVo.setPayMoney(miaoshaOrder.getPayMoney());
            //排队完成 等待支付
            redisTemplate.boundHashOps("currentUserGrabbingStatus").put(userName, miaoshaQueueVo);

            //Put this Order into redis
            this.redisTemplate.boundHashOps("miaoshaOrder").put(userName, miaoshaOrder);
            //过期时间10分钟
            redisTemplate.boundListOps("miaoshaOrder").expire(5, TimeUnit.MINUTES);

            //修改缓存中的商品数据数量
            Long miaoshaGoodCount = redisTemplate.boundHashOps("miaoshaGoodCount").increment(skuId, -1);

            //Down stock
            miaoshaGoodsInRedis.setStock(miaoshaGoodCount.intValue());
            //Add have miaosha(已秒杀数量)

            miaoshaGoodsInRedis.setSpikeTotal( new AtomicInteger(miaoshaGoodsInRedis.getSpikeTotal()).incrementAndGet());
            System.out.println(miaoshaGoodsInRedis.getSpikeTotal());
//            synchronized (this) {
//                miaoshaGoodsInRedis.setSpikeTotal(miaoshaGoodsInRedis.getSpikeTotal() + 1);
//                System.out.println(miaoshaGoodsInRedis.getSpikeTotal());
//            }

            if (miaoshaGoodsInRedis.getStock() <= 0) {
                //Synchronize the  data into mysql
                skuStockMapper.SynchronizeToMysql(miaoshaGoodsInRedis);
                //Same time .delete all redis data
                this.redisTemplate.boundHashOps("miaoshaGood_" + time).delete(skuId);
            } else {
                //update redis data
                this.redisTemplate.boundHashOps("miaoshaGood_" + time).put(skuId, miaoshaGoodsInRedis);
            }
            //The order status is put into the delay queue
            mqService.sendOrderDelayMessage(miaoshaQueueVo);
            return true;
        }

        return false;
    }


    @Override
    public MiaoshaQueueVo getMiaoshaStatus(String userName) {
        MiaoshaQueueVo miaoshaQueueVo = (MiaoshaQueueVo) redisTemplate.boundHashOps("currentUserGrabbingStatus").get(userName);
        return miaoshaQueueVo;
    }

    @Override
    public MiaoshaOrder getMiaoshaOrderStatus(String userName) {
        MiaoshaOrder miaoshaOrder = (MiaoshaOrder) redisTemplate.boundHashOps("miaoshaOrder").get(userName);
        return miaoshaOrder;
    }

    private void cleanQueue(MiaoshaQueueVo miaoshaQueueVo) {
        redisTemplate.boundHashOps("currentUserGrabbingStatus").delete(miaoshaQueueVo.getUserName());

        redisTemplate.boundHashOps("UserQueueCount").delete(miaoshaQueueVo.getUserName());
    }

}
