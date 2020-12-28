package com.lingmeng.service.miaosha;


import com.alibaba.fastjson.JSONObject;
import com.lingmeng.api.miaosha.ImiaoshaService;
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
import java.util.List;


@Slf4j
@Service
public class MiaoshaServiceImpl implements ImiaoshaService {




    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;



    @Autowired
    private SkuStockMapper skuStockMapper;


    @Override
    public List<MiaoshaRedisDataVo> getMiaoshaGoodsByTime(String time) {

        List<MiaoshaRedisDataVo> values = this.redisTemplate.boundHashOps("miaoshaGood_" + time).values();
        return values;
    }

    @Override
    public MiaoshaRedisDataVo getMiaoshaDetail(String time, String id) {
        //本身我们存储进去的就是map,所以取出来也是Map

        MiaoshaRedisDataVo miaoshaRedisDataVo = JSONObject.parseObject(this.redisTemplate.boundHashOps("miaoshaGood_" + time).get(id).toString(), MiaoshaRedisDataVo.class);
        return miaoshaRedisDataVo;
    }

    @Override
//    @Async  //use customize Thread pool
    public Boolean miaoshaCreateOrder() {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        MiaoshaQueueVo miaoshaQueueVo = (MiaoshaQueueVo)redisTemplate.boundListOps("miaoshaQueue").rightPop();

        if(miaoshaQueueVo !=null){

            String time = miaoshaQueueVo.getTime();
            String skuId = miaoshaQueueVo.getSkuId();
            String userName = miaoshaQueueVo.getUserName();



            MiaoshaRedisDataVo miaoshaGoodsInRedis =(MiaoshaRedisDataVo)this.redisTemplate.boundHashOps("miaoshaGood_" + time).get(skuId);
            if(miaoshaGoodsInRedis == null ||miaoshaGoodsInRedis.getStock() ==0 ){
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
            redisTemplate.boundHashOps("currentUserGrabbingStatus").put(userName,miaoshaQueueVo);

            //Put this Order into redis
            this.redisTemplate.boundHashOps("miaoshaOrder").put(userName,miaoshaOrder);
            //Down stock
            miaoshaGoodsInRedis.setStock(miaoshaGoodsInRedis.getStock()-1);
            //Add have miaosha
            miaoshaGoodsInRedis.setSpikeTotal(miaoshaGoodsInRedis.getSpikeTotal() +1);
            if(miaoshaGoodsInRedis.getStock()<=0){
                //Synchronize the  data into mysql
                skuStockMapper.SynchronizeToMysql(miaoshaGoodsInRedis);
                //Same time .delete all redis data
                this.redisTemplate.boundHashOps("miaoshaGood_"+time).delete(skuId);
            }else{
                //update redis data
                this.redisTemplate.boundHashOps("miaoshaGood_"+time).put(skuId,miaoshaGoodsInRedis);
            }
            log.info("返回ture");
            return true;
        }

        return false;
    }
}
