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
public class AliPayCallbackTemplate  extends AbstractPayCallbackTemplate {
    @Override
    protected Map<String, String> verifySignature() {
        //支付宝回调报文伪代码
        log.info(">>>第一步 解析支付宝据报文.....verifySignature()");
        Map<String, String> verifySignature = new HashMap<>();
        verifySignature.put("price", "1000");
        verifySignature.put("orderDes", "充值支付宝会员");
        // 支付状态为1表示为成功....
        verifySignature.put("aliPaymentStatus", PayConstant.PAY_STATUS_SUCCESS);
        verifySignature.put("aliPayOrderNumber", "ali_20190516");
        // 解析报文是否成功 200 为成功..
        verifySignature.put("analysisCode", PayConstant.PAY_RESULT_SUCCESS);
        return verifySignature;
    }

    @Override
    protected String resultFail() {
        return PayConstant.PAY_ALI_FAIL;
    }

    @Override
    protected String asyncService(Map<String, String> verifySignature) {
        log.info(">>>第三步 支付宝支付 asyncService() verifySignatureMap:{}", verifySignature);
        String paymentStatus = verifySignature.get("aliPaymentStatus");
        if (PayConstant.PAY_STATUS_SUCCESS.equals(paymentStatus)) {
            String aliPayOrderNumber = verifySignature.get("aliPayOrderNumber");
            log.info(">>>>orderNumber:{}已经支付成功 修改订单状态为已经支付...",aliPayOrderNumber);
        }
        return resultSuccess();
    }

    @Override
    protected String resultSuccess() {
        return PayConstant.PAY_ALI_SUCCESS;
    }

}
