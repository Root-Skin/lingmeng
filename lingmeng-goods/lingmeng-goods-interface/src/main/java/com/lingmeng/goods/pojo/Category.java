package com.lingmeng.goods.pojo;


import com.lingmeng.base.SuperEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name="lm_category")
@Data
public class Category extends SuperEntity {

    /** 分类名称 */
    private String categoryName ;
    /** 父类ID */
    private String parentId ;
    /** 是否为父节点;0表示否,1表示是 */
    private String isParent ;
    /** 排序指数;越小越靠前 */
    private String sort ;

}
