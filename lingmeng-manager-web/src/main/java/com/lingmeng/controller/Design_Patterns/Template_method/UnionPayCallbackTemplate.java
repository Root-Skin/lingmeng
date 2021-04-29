package com.lingmeng.controller.Design_Patterns.Template_method;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
@Slf4j
@Component
public class UnionPayCallbackTemplate extends AbstractPayCallbackTemplate {
    @Override
    protected Map<String, String> verifySignature() {
        //>>>>银联回调报文伪代码>>>>>>>>
        log.info(">>>>>第一步 解析银联数据报文.....verifySignature()");
        Map<String, String> verifySignature = new HashMap<>();
        verifySignature.put("price", "1000");
        verifySignature.put("orderDes", "充值银联会员");
        // 支付状态为1表示为成功....
        verifySignature.put("unionPaymentStatus", PayConstant.PAY_STATUS_SUCCESS);
        verifySignature.put("unionPayOrderNumber", "union_20190516");
        // 解析报文是否成功 200 为成功..
        verifySignature.put("analysisCode", PayConstant.PAY_RESULT_SUCCESS);
        return verifySignature;
    }

    @Override
    protected String resultFail() {
        return PayConstant.PAY_UNION_FAIL;
    }

    @Override
    protected String resultSuccess() {
        return PayConstant.PAY_UNION_SUCCESS;
    }

    @Override
    protected String asyncService(Map<String, String> verifySignature) {
        log.info(">>>第三步 支付宝支付 asyncService() verifySignatureMap:{}", verifySignature);
        String paymentStatus = verifySignature.get("unionPaymentStatus");
        if (PayConstant.PAY_STATUS_SUCCESS.equals(paymentStatus)) {
            String unionPayOrderNumber = verifySignature.get("unionPayOrderNumber");
            log.info(">>>>orderNumber:{}已经支付成功 修改订单状态为已经支付...",unionPayOrderNumber);
        }
        return resultSuccess();
    }

}
