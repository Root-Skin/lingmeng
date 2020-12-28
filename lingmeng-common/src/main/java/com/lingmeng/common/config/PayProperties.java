package com.lingmeng.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class PayProperties {
    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 生成签名的密钥
     */
    private String key;

    /**
     * 连接超时时间
     */
    private int connectTimeoutMs;

    /**
     * 读取超时时间
     */
    private int readTimeoutMs;

}
