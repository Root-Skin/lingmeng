package com.lingmeng.crawler.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "proxy")
public class proxyConfig {
    private String isUsedProxyIp;
}
