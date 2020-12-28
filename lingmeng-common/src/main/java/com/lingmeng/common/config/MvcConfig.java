package com.lingmeng.common.config;

import com.lingmeng.common.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig  implements WebMvcConfigurer {

    @Autowired
    private JwtProperties jwtProperties;

     /**
      * @Author skin
      * @Date  2020/11/12
      * @Description 向MVC容器中注入 登陆拦截器
      **/
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(jwtProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor()).excludePathPatterns("/item/login.html","/auth/**","/item/**.html","/es/goodsPage");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/cart/**");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/address/**");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/order/**");
    }
}
