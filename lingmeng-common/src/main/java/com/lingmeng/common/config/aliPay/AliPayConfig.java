package com.lingmeng.common.config.aliPay;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @Author skin
 * @Date  2020/12/29
 * @Description 支付宝配置类
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {

   /**
    * 网关
    */
   private String gateWay;

   /**
    * 签名类型
    */
   private String signType;

   /**
    * 编码方式
    */
   private String charset;

   /**
    * 应用 id
    */
   private String appId;
   /**
    * 商户私钥
    */
   private String privateKey;
   /**
    * 通过商户公钥生成的支付宝公钥
    */
   private String alipayPublicKey;

    /**
     * 通过商户公钥生成的支付宝公钥
     */
    private String notifyUrl;

   private servlet servlet;

   private int port = 8080;

   @Data
   public static class servlet {
      private String contextPath = "/api";
   }




}
