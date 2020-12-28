package com.lingmeng.order.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

import java.math.BigDecimal;

@TableName("lm_order")
@Data
public class Order extends SuperEntity {
    /** 用户ID */
    private String userId ;
    /** 买家昵称 */
    private String userNickName ;
    /** 订单状态;(1.未支付 ,2已支付) */
    private Integer status ;
    /** 总金额 */
    private BigDecimal totalPay ;
    /** 实际支付金额 */
    private BigDecimal actualPay ;
    /** 支付类型;(1.在线支付  2.货到付款) */
    private Integer payType ;
    /** 运费 */
    private BigDecimal postFee ;
    /** 物流名称 */
    private String shoppingName ;
    /** 物流单号 */
    private String shoppingOrder ;
    /** 收货人 */
    private String receiver ;
    /** 收货人手机 */
    private String receiverPhone ;
    /** 收货人地区名称 */
    private String receiverArea ;

}
