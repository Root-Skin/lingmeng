package com.lingmeng.model.goods.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;


@TableName("lm_category")
@Data
public class Category extends SuperEntity {

    /** 分类名称 */
    private String categoryName ;
    /** 父类ID */
    private String parentId ;
    /** 是否为父节点;0表示否,1表示是 */
    private Boolean isParent ;
    /** 排序指数;越小越靠前 */
    private String sort ;

}
