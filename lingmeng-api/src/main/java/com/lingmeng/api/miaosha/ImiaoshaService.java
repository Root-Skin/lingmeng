package com.lingmeng.api.miaosha;


import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;

import java.util.List;

public interface ImiaoshaService {

   List<MiaoshaRedisDataVo> getMiaoshaGoodsByTime(String time);

   MiaoshaRedisDataVo getMiaoshaDetail(String time,String id);
    /**
     * @Author skin
     * @Date  2020/12/10
     * @Description 创建秒杀订单
      **/
   Boolean miaoshaCreateOrder();
}
