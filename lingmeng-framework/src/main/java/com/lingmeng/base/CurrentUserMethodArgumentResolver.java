package com.lingmeng.base;

import com.lingmeng.annotation.CurrentUser;
import com.lingmeng.base.lingmengPlug.extension.UserVo;
import com.lingmeng.exception.RestException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author skin
 * @Date  2021/1/27 12:55
 * @description  方法注入
 **/

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserVo.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)   {
        UserVo user = (UserVo) webRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);

        if (user == null) {
            throw new RestException("获取用户信息失败");
        }
        return user;
    }
}
