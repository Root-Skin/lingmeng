package com.lingmeng.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import com.lingmeng.enums.OperateTypeEnum;
import com.lingmeng.enums.ResultTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_lingmeng_log")
public class SysReportLog extends SuperEntity {

    /**
     * 报表名称
     */
    private String name;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求的ip地址
     */
    private String requestIpAddress;

    /**
     * 操作人id
     */
    private String operateUserId;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作结果 成功 or 失败
     */
    private ResultTypeEnum resultType;

    /**
     * 接口执行时间(单位：毫秒)  --Long
     */
    private Long durationTimestamp;

    /**
     * 操作类型 查询 or 导出
     */
    private OperateTypeEnum operateType;

    /**
     * get请求参数
     */
    private String getParams;

    /**
     * post请求参数
     */
    private String postParams;

}
