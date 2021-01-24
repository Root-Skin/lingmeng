package com.lingmeng.controller.order;


import com.lingmeng.api.mq.MqService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.common.Interceptor.LoginInterceptor;
import com.lingmeng.common.utils.auth.UserInfo;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.dao.order.OrderDetailMapper;
import com.lingmeng.dao.order.OrderMapper;
import com.lingmeng.goods.model.SkuStock;
import com.lingmeng.goods.vo.req.CartReq;
import com.lingmeng.order.model.Order;
import com.lingmeng.order.model.OrderDetail;
import com.lingmeng.order.vo.AddOrderReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class GoodsOrder {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private SkuStockMapper skuStockMapper;

    @Autowired
    private MqService mqService;



    //用户创建订单
    @PostMapping("/add")
    public RestReturn addNewOrder(@RequestBody AddOrderReq req) {
        UserInfo user = LoginInterceptor.getLoginUser();

        //填充主表数据
        Order order = new Order();
        order.setActualPay(req.getActualPay());
        order.setTotalPay(req.getTotalPay());
        order.setPayType(req.getPayType());
        order.setReceiver(req.getReceiver());
        order.setUserId(user.getId());
        //拼接地址
        order.setReceiverArea(req.getReceiverProvince() + req.getReceiverCity() + req.getReceiverTown() + req.getReceiverAddress());
        //未支付
        order.setStatus(1);
        //目前还没有生成物流单号(不)
        orderMapper.insert(order);

        //使用死信队列模拟定时任务
        // (每次用户下单的时候，如果订单创建成功，则立即发送一个延时消息到MQ中，
        //等待消息被消费的时候，先检查对应订单是否下单支付成功，如果支付失败,订单进行自动关闭)

        log.info("发送端 发送时间{}:", LocalDateTime.now().toString());
        mqService.normalOrderDelayMessage(order.getId());


        List<CartReq> orderDetails = req.getOrderDetails();

        //生成订单明细表
        for (int i = 0; i <orderDetails.size() ; i++) {
            OrderDetail orderDetail = new OrderDetail();
            //设置未退款
            orderDetail.setIsReturn(1);
            orderDetail.setNums(orderDetails.get(i).getNum());
            orderDetail.setPrice(orderDetails.get(i).getPrice());
            orderDetail.setSpecification(orderDetails.get(i).getOwnSpec());
            orderDetail.setTitle(orderDetails.get(i).getTitle());
            orderDetail.setSkuId(orderDetails.get(i).getSkuId());
            //设置上面生成的主订单的ID
            orderDetail.setOrderId(order.getId());
            orderDetailMapper.insert(orderDetail);


            //减库存
            SkuStock skuStock = skuStockMapper.selectBySkuId(orderDetails.get(i).getSkuId());
            skuStock.setStock(skuStock.getStock()-orderDetails.get(i).getNum());
            skuStockMapper.updateById(skuStock);
        }


        return RestReturn.ok((Object) order.getId());
    }
}
