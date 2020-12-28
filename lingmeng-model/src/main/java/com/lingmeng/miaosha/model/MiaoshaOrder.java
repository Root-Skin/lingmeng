package com.lingmeng.miaosha.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("lm_miaosha_order")
@Data
public class MiaoshaOrder  extends SuperEntity  implements Serializable {
    private String userId ;
    /** 用户姓名*/
    private String userName ;
    /** 秒杀商品ID;秒杀商品ID */
    private String skuId ;
    /** 支付金额 */
    private BigDecimal payMoney ;
    /** 支付时间 */
    private Date payTime ;
    /** 支付状态;(1.未支付 ,2已支付) */
    private Integer status ;
    /** 支付类型;(1.在线支付,2货到付款) */
    private Integer payType ;

}
