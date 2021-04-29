package com.lingmeng.controller.Design_Patterns.Template_method;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
@Slf4j
public abstract class   AbstractPayCallbackTemplate {
    /**
     * 定义共同行为的骨架
     * @return
     */
    public final String asyncCallBack(){
        //1、验证参数与验证签名
        Map<String,String> verifySignature = verifySignature();
        //2、日志收集
        payLog(verifySignature);
        //3、获取验证签名状态
        String analysisCode = verifySignature.get("analysisCode");
        if(!StringUtils.equals(analysisCode, PayConstant.PAY_RESULT_SUCCESS)){
            return resultFail();
        }
        // 4、更改数据库状态同时返回不同支付结果
        return asyncService(verifySignature);
    }

    /**
     * 支付回调验证参数
     * @return
     */
    protected abstract Map<String, String> verifySignature();

    /**
     * 使用多线程写入日志
     *
     * @param verifySignature
     */
    @Async
    protected void payLog(Map<String, String> verifySignature){
        log.info(">>>第二步 写入数据库....verifySignature:{}", verifySignature);
    }

    /**
     * 返回失败结果
     * @return
     */
    protected abstract String resultFail();

    /**
     * 返回成功结果
     * @return
     */
    protected abstract String resultSuccess();

    /**
     * （业务处理）执行修改订单状态和返回不同的结果给对应的客户
     * @param verifySignature
     * @return
     */
    protected abstract String asyncService(Map<String, String> verifySignature);

}
