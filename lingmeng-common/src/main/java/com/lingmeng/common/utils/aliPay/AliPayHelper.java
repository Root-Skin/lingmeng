package com.lingmeng.common.utils.aliPay;


import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.api.good.IskuStockService;
import com.lingmeng.common.config.aliPay.AliPayConfig;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.dao.order.OrderDetailMapper;
import com.lingmeng.dao.order.OrderMapper;
import com.lingmeng.goods.model.SkuStock;
import com.lingmeng.order.model.Order;
import com.lingmeng.order.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author skin
 * @Date 2020/12/29
 * @Description 支付宝支付组件
 **/
@Component
public class AliPayHelper {

    @Autowired
    private AliPayConfig aliPayConfig;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;
    @Autowired
    private IskuStockService skuStockService;


    /**
     * @Author skin
     * @Date 2020/12/29
     * @Description 支付宝创建订单
     **/

    public String createPayUrl(String orderId) {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                    .preCreate("Apple iPhone11 128G", orderId, "20.33");
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                //将二维码返回
                return response.qrCode;
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
                return null;
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * @param orderId
     * @author skin
     * @Date 2021/1/15 12:17
     * @description 超时关闭订单
     **/

    public String timeoutToCloseOrder(String orderId) {


            Order order = orderMapper.selectById(orderId);

            //待支付状态
            if (order.getStatus() == 1) {
                //关闭订单
                order.setStatus(3);
                orderMapper.updateById(order);
                List<OrderDetail> orderDetails = orderDetailMapper.selectList(new QueryWrapper<OrderDetail>().lambda().eq(OrderDetail::getOrderId, order.getId()));
                //获取订单详情中的skuID集合
                List<String> orderSkuIds = orderDetails.stream()
                        .map(OrderDetail::getSkuId)
                        .collect(Collectors.toList());

                //恢复库存
                List<SkuStock> skuStockList = skuStockMapper.selectList(new QueryWrapper<SkuStock>().lambda().in(SkuStock::getSkuId, orderSkuIds));
                for (SkuStock skuStock : skuStockList) {
                    for (OrderDetail orderDetail : orderDetails) {
                        if (skuStock.getSkuId().equals(orderDetail.getSkuId())) {
                            skuStock.setStock(skuStock.getStock() + orderDetail.getNums());
                        }
                    }
                }
                skuStockService.updateBatchById(skuStockList);

            }

        return "success";
    }


    /**
     * @Author skin
     * @Date 2020/12/30
     * @Description 加载配置类到config
     **/
    private Config getOptions() {
        Config config = new Config();
        config.appId = aliPayConfig.getAppId();
        config.gatewayHost = aliPayConfig.getGateWay();
        config.alipayPublicKey = aliPayConfig.getAlipayPublicKey();
        config.signType = aliPayConfig.getSignType();
        config.merchantPrivateKey = aliPayConfig.getPrivateKey();
        config.notifyUrl = aliPayConfig.getNotifyUrl();
        return config;
    }

    /**
     * @return
     * @Author skin
     * @Date 2020/12/30
     * @Description 增加验签功能-异步通知
     */
    public Boolean checkSign(HttpServletRequest request) {

        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for (String value : values) {
                strs = strs + value;
            }
            System.out.println(key + "===>" + strs);
            paramsMap.put(key, strs);
        });
        try {
            return Factory.Payment.Common().verifyNotify(paramsMap);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
