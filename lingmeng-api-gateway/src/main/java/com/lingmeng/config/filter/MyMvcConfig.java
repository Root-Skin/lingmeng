package com.lingmeng.config.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author skin
 * @createTime 2021年02月26日
 * @Description
 */
@Configuration
public class MyMvcConfig  implements WebMvcConfigurer {

    @Bean
    public MyInterceptor getMyInterceptor(){
        System.out.println("注入了MyInterceptor");
        return new MyInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
    }
}
