package com.lingmeng.miaosha.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MiaoshaQueueVo  implements Serializable {
    /** 用户名字 */
    private String userName;
    /** 创建时间 */
    private Date   createTime;
    /** 秒杀状态 (1:排队中 ,2:秒杀等待支付,3:支付超时,4:秒杀失败,5:支付完成)*/
    private Integer miaoshaStatus;
    /** 商品skuID */
    private String skuId;
    /** 支付金额 */
    private BigDecimal payMoney;
    /** 订单Id */
    private String orderId;
    /** 时间段 */
    private String time;

     /**
      * @Author skin
      * @Date  2020/12/11
      * @Description 生成不含有支付金额和订单ID的构造方法
      **/
    public MiaoshaQueueVo(String userName, Date createTime, Integer miaoshaStatus, String skuId, String time) {
        this.userName = userName;
        this.createTime = createTime;
        this.miaoshaStatus = miaoshaStatus;
        this.skuId = skuId;
        this.time = time;
    }
}
