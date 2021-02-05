package com.lingmeng.order.vo;


import com.lingmeng.goods.vo.req.CartReq;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddOrderReq {


    /** 收货人 */
    private String receiver;
    /** 收货人所在省 */
    private String receiverProvince;
    /** 收货人所在城市 */
    private String receiverCity;
    /** 收货人所在县/区 */
    private String receiverTown;
    /** 收货人具体地址 */
    private String receiverAddress;
    /** 收货人电话号码 */
    private String receiverMobile;

    /** 订单相关商品(购物车中的数据) */
    private List<CartReq> orderDetails;


    /** 总金额 */
    private BigDecimal totalPay;

    /** 实际支付金额 */
    private BigDecimal actualPay;

    /** 支付类型 (1.在线支付  2.货到付款) */
    private Integer payType;





}
