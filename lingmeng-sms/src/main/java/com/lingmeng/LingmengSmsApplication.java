package com.lingmeng;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LingmengSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingmengSmsApplication.class,args);
    }
}
