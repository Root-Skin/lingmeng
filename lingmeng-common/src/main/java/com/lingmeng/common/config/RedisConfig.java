package com.lingmeng.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private String host = "192.168.189.134";
    private int port = 6379;
    private String password ;
    private int timeout = 2000;
    private int database = 0;
    private RedisConfig.Pool pool = new Pool();



    @Data
    public static class Pool {
        private int maxActive = 200;
        private int maxWait = -1;
        private int maxIdle = 8;
        private int minIdle = 0;
    }

}
