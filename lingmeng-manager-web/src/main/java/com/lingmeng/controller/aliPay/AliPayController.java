package com.lingmeng.controller.aliPay;


import com.lingmeng.api.miaosha.ImiaoshaOrderService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.common.utils.aliPay.AliPayHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aliPay")
public class AliPayController {


    @Autowired
    private ImiaoshaOrderService imiaoshaOrderService;

    @Autowired
    private AliPayHelper aliPayHelper;


    @GetMapping("/shoppingCartAliPay")
    public RestReturn shoppingCartWeChatPay(@RequestParam String orderId) {

        //调用微信支付返回 支付地址
        String paymentAddress = aliPayHelper.createPayUrl(orderId);
        if(StringUtils.isEmpty(paymentAddress)){
            return RestReturn.ok("生成付款失败");
        }
        return RestReturn.ok(paymentAddress,"操作成功");
    }




}
