package com.lingmeng.goods.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_specification")
@Data
public class Specification  extends SuperEntity {

    private String categoryId ;
    /** 规格(json) */
    private String specifications ;

}
