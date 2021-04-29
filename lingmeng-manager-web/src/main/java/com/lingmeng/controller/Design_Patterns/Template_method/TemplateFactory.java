package com.lingmeng.controller.Design_Patterns.Template_method;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
public class TemplateFactory {
    public static AbstractPayCallbackTemplate getPayCallbackTemplate(String templateId){
        return SpringUtils.getBean(templateId,AbstractPayCallbackTemplate.class);
    }
}
