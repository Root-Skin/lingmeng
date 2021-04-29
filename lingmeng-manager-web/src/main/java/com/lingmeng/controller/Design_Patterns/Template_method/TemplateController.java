package com.lingmeng.controller.Design_Patterns.Template_method;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
@RestController
public class TemplateController {
    /**
     * 支付回调
     * @return
     */
    @RequestMapping("/asyncCallBack")
    public String asyncCallBack(String templateId) {
        AbstractPayCallbackTemplate payCallbackTemplate = TemplateFactory.getPayCallbackTemplate(templateId);
        // 使用模版方法模式 执行共同的骨架
        return payCallbackTemplate.asyncCallBack();
    }

}
