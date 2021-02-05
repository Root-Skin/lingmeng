package com.lingmeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableZuulProxy
@ComponentScan(basePackages ={"com.lingmeng.config","com.lingmeng.api", "com.lingmeng.config"})
public class LingmengApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(LingmengApiGateway.class,args);
    }
}
