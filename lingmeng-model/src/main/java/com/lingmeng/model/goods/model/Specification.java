package com.lingmeng.model.goods.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("lm_specification")
@Data
public class Specification  {

    private String categoryId ;
    /** 规格(json) */
    private String specifications ;
    /** 创建人 */
    private String createdBy ;
    /** 创建时间 */
    private Date createdTime ;
    /** 更新人 */
    private String updatedBy ;
    /** 更新时间 */
    private Date updatedTime ;
    /** 是否删除 */
    private Boolean delFlag ;
}
