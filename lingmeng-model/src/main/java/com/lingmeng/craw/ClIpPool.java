package com.lingmeng.craw;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("ip_pool")
@Data
public class ClIpPool  extends SuperEntity {

    private String supplier;

    private String ip;
    /**
     * 端口号
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 错误码
     */
    private Integer code;

    /**



     * 耗时
     */
    private Integer duration;

    /**
     * 错误信息
     */
    private String error;

    private Boolean isEnable;

    private String ranges;

}
