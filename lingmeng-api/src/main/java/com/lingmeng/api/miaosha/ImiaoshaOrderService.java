package com.lingmeng.api.miaosha;


import com.lingmeng.miaosha.model.MiaoshaOrder;
import com.lingmeng.miaosha.vo.MiaoshaQueueVo;

public interface ImiaoshaOrderService {


    /**
     * @Author skin
     * @Date  2020/12/10
     * @Description 创建秒杀订单
      **/
   Boolean miaoshaCreateOrder();
     /**
      * @Author skin
      * @Date  2020/12/21
      * @Description 查询抢购状态
      **/
    MiaoshaQueueVo getMiaoshaStatus(String userName);

    MiaoshaOrder getMiaoshaOrderStatus(String userName);
}
