package com.lingmeng.config.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author skin
 * @createTime 2021年02月26日
 * @Description
 */
@Component
public class myFilter implements Filter {


    @Autowired
    private TestService testService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("Filter 前置");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Filter 处理中");
        testService.a();
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("Filter 后置");
    }
}

