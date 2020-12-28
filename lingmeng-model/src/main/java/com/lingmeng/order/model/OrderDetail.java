package com.lingmeng.order.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

import java.math.BigDecimal;


@TableName("lm_order_detail")
@Data
public class OrderDetail  extends SuperEntity {
    /** 主订单ID */
    private String orderId ;
    /** 商品Id */
    private String skuId ;
    /** 商品标题 */
    private String title ;
    /** 商品规格 */
    private String specification ;
    /** 单价 */
    private BigDecimal price ;
    /** 数量 */
    private Integer nums ;
    /** 是否退货;(1.未退款 2.已申请 3.已退款) */
    private Integer isReturn ;
}
