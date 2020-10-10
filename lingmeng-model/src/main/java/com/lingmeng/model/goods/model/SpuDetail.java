package com.lingmeng.model.goods.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private String desctiption ;
    /** 全部规格参数数据 */
    private String specification ;
    /** 特有规格参数 */
    private String uniqueSpecification ;
    /** 包装清单 */
    private String packageList ;
    /** 售后服务 */
    private String aftersalesService ;
}
