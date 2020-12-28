package com.lingmeng.controller.order;


import com.lingmeng.base.RestReturn;
import com.lingmeng.common.Interceptor.LoginInterceptor;
import com.lingmeng.common.utils.auth.UserInfo;
import com.lingmeng.dao.order.OrderDetailMapper;
import com.lingmeng.dao.order.OrderMapper;
import com.lingmeng.goods.vo.req.CartReq;
import com.lingmeng.order.model.Order;
import com.lingmeng.order.model.OrderDetail;
import com.lingmeng.order.vo.AddOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class GoodsOrder {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

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

        }


        return RestReturn.ok((Object) order.getId());
    }
}
