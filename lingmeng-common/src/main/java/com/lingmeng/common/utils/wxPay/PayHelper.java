package com.lingmeng.common.utils.wxPay;


import com.github.wxpay.sdk.WXPay;
import com.lingmeng.common.config.PayConfig;
import com.lingmeng.dao.order.OrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class PayHelper {

    private WXPay wxPay;

    private static final Logger logger = LoggerFactory.getLogger(PayHelper.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OrderMapper orderMapper;


    //初始化构造方法的作用
    public PayHelper(PayConfig payConfig) {
        // 真实开发时
        wxPay = new WXPay(payConfig);
        // 测试时(使用沙箱环境)
//         wxPay = new WXPay(payConfig, WXPayConstants.SignType.MD5, true);
    }

    public String createPayUrl(String orderId) {
        String key = "lingmengPay." + orderId;
        try {
            String url = this.redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(url)) {
                return url;
            }
        } catch (Exception e) {
            logger.error("查询缓存付款链接异常,订单编号：{}", orderId, e);
        }

        try {
            Map<String, String> data = new HashMap<>();
            // 商品描述
            data.put("body", "玲梦商城测试");
            // 订单号
            data.put("out_trade_no", orderId);
            //货币
            data.put("fee_type", "CNY");
            //金额，单位是分
            data.put("total_fee", "1");
            //调用微信支付的终端IP（estore商城的IP）
            data.put("spbill_create_ip", "127.0.0.1");
            //回调地址
            data.put("notify_url", "http://test.lingmeng.com/wxpay/notify");
            // 交易类型为扫码支付
            data.put("trade_type", "NATIVE");
            //商品id,使用假数据
            //trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
            data.put("product_id", "1234567");

            Map<String, String> result = this.wxPay.unifiedOrder(data);

            if ("SUCCESS".equals(result.get("return_code"))) {
                String url = result.get("code_url");
                // 将付款地址缓存，时间为10分钟
                try {
                    this.redisTemplate.opsForValue().set(key, url, 10, TimeUnit.MINUTES);
                } catch (Exception e) {
                    logger.error("缓存付款链接异常,订单编号：{}", orderId, e);
                }
                return url;
            } else {
                logger.error("创建预交易订单失败，错误信息：{}", result.get("return_msg"));
                return null;
            }
        } catch (Exception e) {
            logger.error("创建预交易订单异常", e);
            return null;
        }
    }

    /**
     * 查询订单状态
     *
     * @param orderId
     * @return
     */
    public Map queryOrder(String orderId) {
        Map<String, String> data = new HashMap<>();
        // 订单号
        data.put("out_trade_no", orderId);
        Map result2 = new HashMap();
        try {
            Map<String, String> result = this.wxPay.orderQuery(data);
            if (result == null) {
                // 未查询到结果，认为是未付款
                result2.put("status",PayState.NOT_PAY.getValue());
                return result2;
            }
            String state = result.get("trade_state");
            if ("SUCCESS".equals(state)) {
                // success，则认为付款成功

                // 修改订单状态
                this.orderMapper.updateOrderStatus(orderId);
                result2.put("status",PayState.SUCCESS.getValue());
                result2.put("payMoney",result.get("total_fee"));
                return  result2;
            } else if (StringUtils.equals("USERPAYING", state) || StringUtils.equals("NOTPAY", state)) {
                // 未付款或正在付款，都认为是未付款
                 result2.put("status",PayState.NOT_PAY.getValue());
                return  result2;
            } else {
                // 其它状态认为是付款失败
                result2.put("status",PayState.FAIL.getValue());
                return  result2;
            }
        } catch (Exception e) {
            logger.error("查询订单状态异常", e);
            result2.put("status",PayState.NOT_PAY.getValue());
            return  result2;
        }
    }
}
