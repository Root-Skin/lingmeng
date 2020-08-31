package com.lingmeng;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lingmeng.goods.mapper")
public class LingmengItemService {
    public static void main(String[] args) {
        SpringApplication.run(LingmengItemService.class,args);
    }
}

