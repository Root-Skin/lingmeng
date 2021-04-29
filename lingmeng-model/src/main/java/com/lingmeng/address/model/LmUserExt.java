package com.lingmeng.address.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_user_ext")
@Data
public class LmUserExt  extends SuperEntity {
    /** 用户ID */
    private String userId ;

    private String receiver ;
    /** 省 */
    private String province ;
    /** 市 */
    private String city ;
    /** 区/县 */
    private String town ;
    /** 详细地址 */
    private String address ;
    /** 地址别名 */
    private String alias ;
    /** 电话 */
    private String phone ;
    /** 邮箱 */
    private String email ;
    /** 是否默认;( 0.否  1.默认 ) */
    private Integer isDefault ;

}
