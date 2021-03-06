package com.lingmeng.goods.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

/**
  * @Author skin
  * @Date  2020/9/30
  * @Description 该表是扩展的子表
  **/
@TableName("lm_spu_detail")
@Data
public class SpuDetail extends SuperEntity {

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


}
