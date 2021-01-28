package com.lingmeng.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author skin
 * @Date  2021/1/27 20:01
 * @description  七牛相关配置
 **/
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuConfig {
    /**
     * 3D模型对应的七牛AccessKey
     */
    private String realFaceAccessKey;
    /**
     * 3D模型对应的七牛SecretKey
     */
    private String realFaceSecretKey;
    /**
     * 3D模型对应的七牛空间名称
     */
    private String realFaceBucket;
    /**
     * 3D模型对应的七牛域名地址
     */
    private String realFaceDomain;
    /**
     * 3D模型对应的七牛访问文件的连接协议
     */
    private String realFaceProtocol;


    /**
     * 附件对应的七牛AccessKey
     */
    private String accessKey;
    /**
     * 附件对应的七牛SecretKey
     */
    private String secretKey;
    /**
     * 附件对应的七牛空间名称
     */
    private String bucket;
    /**
     * app3D对应的七牛空间名称
     */
    private String appModelBucket = null;
    /**
     * app3D对应的七牛域名地址
     */
    private String appModelDomain = null;
    /**
     * 附件对应的七牛域名地址
     */
    private String domain;
    /**
     * 附件对应的七牛访问文件的连接协议
     */
    private String protocol;
    /**
     * 录音地址
     */
    private String audioDomain;
    /**
     * 录音上传空间
     */
    private String audioBucket;

    /**
     * 是否购买模型的使用
     */
    private Boolean purchaseModel = false;
}
