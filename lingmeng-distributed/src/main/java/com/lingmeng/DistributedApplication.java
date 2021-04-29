package com.lingmeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author skin
 * @createTime 2021年04月27日
 * @Description 分布式远程调用启动类
 */

@SpringBootApplication
@ComponentScan(basePackages ={"com.lingmeng.controller",
        "com.lingmeng.dao"
})
@MapperScan("com.lingmeng.dao")
public class DistributedApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributedApplication.class, args);
    }
}
