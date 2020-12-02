package com.lingmeng.goods.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_sku_stock")
@Data
public class SkuStock   extends SuperEntity {

    /** sku的ID */
    private String skuId ;
    /** 可秒杀库存 */
    private Integer spikeStock ;
    /** 秒杀总数量 */
    private Integer spikeTotal ;
    /** 库存数量 */
    private Integer stock ;


}
