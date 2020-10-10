package com.lingmeng.model.goods.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_spu")
@Data
public class Spu extends SuperEntity {
    /** 标题 */
    private String title ;
    /** 子标题 */
    private String subtitle ;
    /** 一级类目ID */
    private String cid1 ;
    /** 二级类目ID */
    private String cid2 ;
    /** 三级类目ID */
    private String cid3 ;
    /** 关联品牌ID */
    private String brandId ;
    /** 是否上架 */
    private Boolean shelfFlag ;
}
