package com.lingmeng;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

//com.lingmeng.* 能够扫描到全局异常处理,不能解决跨域

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lingmeng.dao")
@EnableAsync
@ComponentScan(basePackages ={"com.lingmeng.api","com.lingmeng.dao","com.lingmeng.service",
        "com.lingmeng.controller","com.lingmeng.common","com.lingmeng.*.*","com.lingmeng.exception","com.lingmeng.task"})
public class LingmengManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingmengManagerApplication.class,args);
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
