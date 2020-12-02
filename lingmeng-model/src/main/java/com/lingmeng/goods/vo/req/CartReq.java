package com.lingmeng.goods.vo.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartReq  implements Serializable {
    private String userId;// 用户id
    private String skuId;// 商品id
    private String title;// 标题
    private String image;// 图片
    private BigDecimal price;// 加入购物车时的价格
    private Integer num;// 购买数量
    private String ownSpec;// 商品规格参数
}
