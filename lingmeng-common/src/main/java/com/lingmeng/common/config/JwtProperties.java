package com.lingmeng.common.config;

import com.lingmeng.common.utils.auth.RsaUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
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

    private String secret;

    private String pubKeyPath;

    private String priKeyPath;

    private int expire;// token过期时间


    //cookie扩展
    private String cookieName;

    private int cookieMaxAge;


    //扩展字段
    private PublicKey publicKey;

    private PrivateKey privateKey; // 私钥

    /**
     * @Author skin
     * @Date 2020/8/28
     * @Description 在执行构造方法之后执行
     **/
    @PostConstruct
    public void init() {
        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }

            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            logger.error("初始化公钥失败", e);
            throw new RuntimeException();
        }

    }

}
