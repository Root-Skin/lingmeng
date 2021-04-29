package com.lingmeng.controller.wxPay;


import com.lingmeng.api.miaosha.ImiaoshaOrderService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.common.utils.wxPay.PayHelper;
import com.lingmeng.miaosha.model.MiaoshaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {


    @Autowired
    private ImiaoshaOrderService imiaoshaOrderService;

    @Autowired
    private PayHelper payHelper;

    /**
     * @Author skin
     * @Date 2020/12/21
     * @Description 创建二维码(秒杀相关,和)
     **/
    @GetMapping("/CreateQR")
    public RestReturn CreateQR() {
        String userName = "skin";
        MiaoshaOrder miaoshaOrderStatus = imiaoshaOrderService.getMiaoshaOrderStatus(userName);
        if (miaoshaOrderStatus != null) {
            if (userName.equals(miaoshaOrderStatus.getUserName())) {
                return null;
            }
            return null;
        }
        return null;
    }


    /**
     * @Author skin
     * @Date 2020/12/24
     * @Description 用户购物车下单 调用支付
     **/
    @GetMapping("/shoppingCartWeChatPay")
    public RestReturn shoppingCartWeChatPay(@RequestParam String orderId) {
        //调用微信支付返回 支付地址
        String paymentAddress = payHelper.createPayUrl(orderId);
        if(StringUtils.isEmpty(paymentAddress)){
            return RestReturn.ok("生成付款失败");
        }
        return RestReturn.ok(paymentAddress,"操作成功");
    }
    /**
     * @Author skin
     * @Date 2020/12/24
     * @Description 查询订单支付状态
     **/
    @GetMapping("/shoppingCartWeChatPayStatus")
    public RestReturn queryOrder(@RequestParam String orderId) {
        //查询支付状态
        Map map = payHelper.queryOrder(orderId);

        return RestReturn.ok(map);
    }
}
