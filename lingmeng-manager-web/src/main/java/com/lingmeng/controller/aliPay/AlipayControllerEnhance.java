package com.lingmeng.controller.aliPay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lingmeng.base.RestReturn;
import com.lingmeng.common.config.aliPay.WebSocket;
import com.lingmeng.common.utils.aliPay.AliPayHelper;
import com.lingmeng.dao.order.OrderMapper;
import com.lingmeng.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * @Author skin
 * @Date 2020/12/30
 * @Description 带有异步回调功能的支付
 **/
@Controller
@Slf4j
public class AlipayControllerEnhance {


    @Autowired
    private AliPayHelper aliPayHelper;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private OrderMapper orderMapper;



    @GetMapping("/aliPayEnhance/shoppingCartAliPay")
    @ResponseBody
    public RestReturn shoppingCartWeChatPay(@RequestParam String orderId) {

        String paymentAddress = aliPayHelper.createPayUrl(orderId);
        if (StringUtils.isEmpty(paymentAddress)) {
            return RestReturn.ok("生成二维付款码链接地址失败");
        }
        return RestReturn.ok(paymentAddress, "操作成功");
    }


    /**
     * @Author skin
     * @Date 2020/12/30
     * @Description 用户扫描付款码后的回调函数
     **/
    @RequestMapping("/call")
    public void call(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("type=text/html;charset=UTF-8");
        log.info("支付宝的的回调函数被调用");
        try {
            if (!aliPayHelper.checkSign(request)) {
                log.info("验签失败");
                response.getWriter().write("failture");
                return;
            }
            //表示支付成功状态下的操作
            Map<String, String[]> parameterMap = request.getParameterMap();
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(parameterMap));
            String trade_status = JSONArray.parseArray(jsonObject.get("trade_status").toString(), String.class).get(0);
            String orderId = JSONArray.parseArray(jsonObject.get("trade_no").toString(), String.class).get(0);

            ;
            if (trade_status.equals("TRADE_SUCCESS")) {
                log.info("支付宝的支付状态为TRADE_SUCCESS");
                Order order = orderMapper.selectById(orderId);
                order.setStatus(2);

                //业务逻辑处理 ,webSocket在下面会有介绍配置
                webSocket.sendMessage("true");
            }
            response.getWriter().write("success");
        } catch (IOException e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * @author skin
     * @param orderId
     * @Date  2021/1/15 12:21
     * @description  超时关闭订单
     **/
    @GetMapping("/aliPayEnhance/timeoutToCloseOrder")
    @ResponseBody
    public RestReturn timeoutToCloseOrder(@RequestParam String orderId) {

        //调用微信支付返回 支付地址
        String paymentAddress = aliPayHelper.timeoutToCloseOrder(orderId);
        if (StringUtils.isEmpty(paymentAddress)) {
            return RestReturn.ok("关闭订单失败");
        }
        return RestReturn.ok(paymentAddress, "操作成功");
    }



}
