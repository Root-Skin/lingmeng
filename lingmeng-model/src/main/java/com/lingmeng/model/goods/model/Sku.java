package com.lingmeng.model.goods.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

import java.math.BigDecimal;

@TableName("lm_sku")
@Data
public class Sku   extends SuperEntity {
    /** 关联的spu的ID */
    private String spuId ;
    /** 标题 */
    private String title ;
    /** 图片 */
    private String image ;
    /** 价格销售 */
    private BigDecimal price;
    /** 下标组合 */
    private String indexes ;
    /** sku的特有规格参数，json格式，反序列化时应使用linkedHashMap，保证有序 */
    private String ownSpec ;
    /** 是否有效 */
    private Boolean enable ;
    /** 库存数 */
    @TableField(exist = false)
    private Integer stock;
}
