package com.lingmeng.crawler.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "crux.cookie")
public class CruxConfig {

    private String name;
}
