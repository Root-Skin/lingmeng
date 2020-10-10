package com.lingmeng.model.goods.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_brand")
@Data
public class Brand extends SuperEntity {
    /** 品牌名字 */
    private String brandName ;
    /** 品牌图片 */
    private String brandPic ;
    /** 品牌首字母 */
    private String brandLetter ;
}
