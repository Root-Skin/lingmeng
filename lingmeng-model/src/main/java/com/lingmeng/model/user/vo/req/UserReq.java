package com.lingmeng.model.user.vo.req;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
public class UserReq {
    /** 用户名 */
    @Length(min = 4,max = 30,message = "用户名字只能在4-30位之间")
    private String userName ;
    /** 密码 */
    @Length(min = 4,max = 30,message = "用户名字只能在4-30位之间")
    private String password ;
    /** 电话号码 */
    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone ;

    private String email ;
    /** 验证码 */
    private String code;
}
