package com.lingmeng.script;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
  * @Author skin
  * @Date  2021/1/10
  * @Description 启动后,执行脚本的记录
  **/
@Data
@TableName("sys_executed_methods")
@Accessors(chain = true)
public class SysExecutedMethods extends SuperEntity {

    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 注解执行时间
     */
    private String methodTime;
    /**
     * 注解执行参数
     */
    private String methodParams;
    /**
     * 状态，见：lifeCycleEnum
     */
    private LifeCycleEnum status;




}
