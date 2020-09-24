package com.lingmeng.model.user.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_user")
@Data
public class User  extends SuperEntity {
    /** 用户名 */

    private String userName ;
    /** 密码 */
    private String password ;
    /** 电话号码 */
    private String phone ;
    /** email */
    private String email ;
}
