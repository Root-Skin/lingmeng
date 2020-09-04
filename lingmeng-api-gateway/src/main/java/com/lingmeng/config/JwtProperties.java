package com.lingmeng.config;


import com.lingmeng.utils.RsaUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PublicKey;


/**
 * @Author skin
 * @Date 2020/8/28
 * @Description 属性类加载数据
 **/
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    private String pubKeyPath;

    //扩展字段
    private PublicKey publicKey;

    //cookie扩展
    private String cookieName;

    /**
     * @Author skin
     * @Date 2020/8/28
     * @Description 在执行构造方法之后执行
     **/
    @PostConstruct
    public void init() {
        try {
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            logger.error("初始化公钥失败", e);
            throw new RuntimeException();
        }

    }

}
