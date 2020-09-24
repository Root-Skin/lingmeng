package com.lingmeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

//com.lingmeng.* 能够扫描到全局异常处理,不能解决跨域

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lingmeng.dao")
@ComponentScan(basePackages ={"com.lingmeng.api","com.lingmeng.dao","com.lingmeng.service",
        "com.lingmeng.controller","com.lingmeng.common","com.lingmeng.*.*","com.lingmeng.exception"})
public class LingmengManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingmengManagerApplication.class,args);
    }
}
