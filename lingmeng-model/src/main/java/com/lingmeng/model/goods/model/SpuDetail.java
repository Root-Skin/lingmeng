package com.lingmeng.model.goods.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
  * @Author skin
  * @Date  2020/9/30
  * @Description 该表是扩展的子表
  **/
@TableName("lm_spu_detail")
@Data
public class SpuDetail {

    private String spuId ;
    /** 描述 */
    private String description ;
    /** 全部规格参数数据 */
    private String specifications ;
    /** 特有规格参数 */
    private String uniqueSpecification ;
    /** 包装清单 */
    private String packageList ;
    /** 售后服务 */
    private String afterSalesService ;

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
