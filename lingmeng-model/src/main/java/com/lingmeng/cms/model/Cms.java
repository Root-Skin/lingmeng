package com.lingmeng.cms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_site")
@Data
public class Cms  extends SuperEntity {
    /** 站点ID */
    private Integer  msgMethod ;


    @TableField(exist = false)
    private String  msgMethodStatus;

}
