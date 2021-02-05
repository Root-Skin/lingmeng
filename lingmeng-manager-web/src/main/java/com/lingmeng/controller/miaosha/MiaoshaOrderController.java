package com.lingmeng.controller.miaosha;

import com.lingmeng.api.miaosha.ImiaoshaOrderService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.miaosha.MiaoshaOrderMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.miaosha.vo.MiaoshaQueueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/miaoshaOrder")
public class MiaoshaOrderController {

    @Autowired
    private ImiaoshaOrderService imiaoshaOrderService;
    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @author skin
     * @param id  商品SKu的 ID
     * @param time 所属秒杀时间段
     * @Date  2021/1/17 16:48
     * @description 秒杀下单
     **/
    @GetMapping("/miaoshaCreateOrder")
    public RestReturn miaoshaCreateOrder(String id, String time) {

        String userName = "skin";

        //判断是否有库存
        Long size = redisTemplate.boundListOps("miaoshaGoodCountList_" + id).size();
        if (size == null || size <= 0) {
            //没有库存了
            throw new RestException("10000");
        }


        //使用redis自增 判断该用户是否排队(所以用户名字不可以重复)
        Long userQueueCount = redisTemplate.boundHashOps("UserQueueCount").increment(userName, 1);
        if (userQueueCount > 1) {
            //表示重复排队了
            throw new RestException("10001");
        }

        MiaoshaQueueVo miaoshaQueueVo = new MiaoshaQueueVo(userName, new Date(), 1, id, time);
        //Put the Queuing information into redis
        redisTemplate.boundListOps("miaoshaQueue").leftPush(miaoshaQueueVo);

        //将具体用户的抢单状态 放入Hash(排队中的订单)
        redisTemplate.boundHashOps("currentUserGrabbingStatus").put(userName, miaoshaQueueVo);

        Boolean spikeResult = imiaoshaOrderService.miaoshaCreateOrder();
        if (spikeResult) {
            return RestReturn.ok("Successful order");
        }
        return RestReturn.error("Failed to grab the order");
    }

    /**
     * @Author skin
     * @Date 2020/12/11
     * @Description 得到当前用户秒杀抢单状态(1 : 排队中, 2 : 秒杀等待支付, 3 : 支付超时, 4 : 秒杀失败, 5 : 支付完成)
     **/
    @GetMapping("/getMiaoshaStatus")
    public RestReturn getMiaoshaStatus() {
        String userName = "skin";
        MiaoshaQueueVo miaoshaStatus = imiaoshaOrderService.getMiaoshaStatus(userName);
        if (miaoshaStatus == null) {
            return RestReturn.error("不存在该用户的抢购信息");
        }
        return RestReturn.ok(miaoshaStatus, "抢单状态");
    }

    /**
     * @author skin
     * @param id
     * @param time
     * @Date  2021/1/17 16:51
     * @description jmeter多用户抢单
     **/
    @GetMapping("/miaoshaCreateOrder2")
    public RestReturn miaoshaCreateOrder(String id, String time,String userName) {

        //判断是否有库存
        Long size = redisTemplate.boundListOps("miaoshaGoodCountList_" + id).size();
        if (size == null || size <= 0) {
            //没有库存了
            throw new RestException("10000");
        }


        //使用redis自增 判断该用户是否排队(所以用户名字不可以重复)
        Long userQueueCount = redisTemplate.boundHashOps("UserQueueCount").increment(userName, 1);
        //设置过期事时间10s
        redisTemplate.boundHashOps("UserQueueCount").expire(2, TimeUnit.MINUTES);
        if (userQueueCount > 1) {
            //表示重复排队了
            throw new RestException("10001");
        }

        MiaoshaQueueVo miaoshaQueueVo = new MiaoshaQueueVo(userName, new Date(), 1, id, time);
        //Put the Queuing information into redis
        redisTemplate.boundListOps("miaoshaQueue").leftPush(miaoshaQueueVo);
        //设置过期时间10分钟
        redisTemplate.boundListOps("miaoshaQueue").expire(5,TimeUnit.MINUTES);

        //将具体用户的抢单状态 放入Hash(排队中的订单)
        redisTemplate.boundHashOps("currentUserGrabbingStatus").put(userName, miaoshaQueueVo);

        //设置过期时间10分钟
        redisTemplate.boundListOps("currentUserGrabbingStatus").expire(5,TimeUnit.MINUTES);

        Boolean spikeResult = imiaoshaOrderService.miaoshaCreateOrder();
        if (spikeResult) {
            return RestReturn.ok("Successful order");
        }
        return RestReturn.error("Failed to grab the order");
    }
}
