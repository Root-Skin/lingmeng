package com.lingmeng.config;

import com.lingmeng.utils.CookieUtils;
import com.lingmeng.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
  * @Author skin
  * @Date  2020/8/31
  * @Description zuul过滤器
  **/
@Component
public class LoginFilter extends ZuulFilter {
    private static  final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    JwtProperties properties;

    @Autowired
    FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";  //表示前置过滤器
    }

    @Override
    public int filterOrder() {
        return 5;  //默认为0 ,数字越大,优先级越低
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        return  !isAllowPath(requestURI); //是否执行该过滤器(true 说明需要过滤)
    }

    private  boolean isAllowPath(String requestURI){
         boolean flag = false;
         for(String path :filterProperties.getAllowPaths()){
             if(requestURI.startsWith(path)){
                 flag = true;
                 break;
             }
         }
         return flag;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = CookieUtils.getCookieValue(request, properties.getCookieName());
        try {
            //通过后就进项放行
            JwtUtils.getInfoFromToken(token, properties.getPublicKey());
        } catch (Exception e) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(403);
            logger.error("非法访问,未登陆,地址:{}",request.getRemoteHost(),e);
        }
        return null;
    }
}
