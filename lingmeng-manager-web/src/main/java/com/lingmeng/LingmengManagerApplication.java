package com.lingmeng;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.lingmeng.api.sms.MailService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//com.lingmeng.* 能够扫描到全局异常处理,不能解决跨域

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lingmeng.dao")
@EnableAsync
@Slf4j
@ComponentScan(basePackages = {"com.lingmeng.api", "com.lingmeng.dao", "com.lingmeng.service",
        "com.lingmeng.controller", "com.lingmeng.common", "com.lingmeng.*.*", "com.lingmeng.exception",
        "com.lingmeng.task",
        "com.lingmeng.crawler",
        "com.lingmeng.Interceptor",
        "com.lingmeng.cache",
        "com.lingmeng.anotation",
        "com.lingmeng.constant",
        "com.lingmeng.demo",
        "com.lingmeng.aspect",
        "com.lingmeng.enums",
        "com.lingmeng.script",
        "com.lingmeng.sqlScript",
        "com.lingmeng.qiniu",
        "com.lingmeng.testEnum",
        "com.lingmeng.report"
})
public class LingmengManagerApplication {


    private static MailService mailService;


    private static AmqpTemplate amqpTemplate;

    @Autowired
    public void mailService(MailService mailService) {
        LingmengManagerApplication.mailService = mailService;
    }

    @Autowired
    public void amqpTemplate(AmqpTemplate amqpTemplate) {
        LingmengManagerApplication.amqpTemplate = amqpTemplate;
    }


    public static void main(String[] args) {

        Long startTime = System.currentTimeMillis();
        SpringApplication.run(LingmengManagerApplication.class, args);
        Long endTime = System.currentTimeMillis();

        log.info("LingMeng is success!   启动所花时间：" + (endTime - startTime));
        sendEmail();
    }

    /**
     * @param
     * @author skin
     * @Date 2021/1/18 17:44
     * @description 发送邮件
     **/
    private static void sendEmail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String message = "local" + "启动时间：" + LocalDateTime.now().format(formatter);
        //异步发送启动日志
        amqpTemplate.convertAndSend("lingmeng.startLog.exchange", "startLog", message);

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
