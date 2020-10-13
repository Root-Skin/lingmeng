package com.lingmeng.model.goods.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("lm_sku_stock")
@Data
public class SkuStock   {

    /** sku的ID */
    private String skuId ;
    /** 可秒杀库存 */
    private Integer spikeStock ;
    /** 秒杀总数量 */
    private Integer spikeTotal ;
    /** 库存数量 */
    private Integer stock ;

    /** 创建人 */
    private String createdBy ;
    /** 创建时间 */
    private Date createdTime ;
    /** 更新人 */
    private String updatedBy ;
    /** 更新时间 */
    private Date updatedTime ;
    /** 刪除 标识位*/
    private Boolean delFlag  =false;
}
