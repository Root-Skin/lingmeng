package com.lingmeng.timed;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

import java.util.Date;


 /**
  * @Author skin
  * @Date  2021/1/7
  * @Description 定时任务记录表
  **/
@Data
@TableName("time_task")
public class Task extends SuperEntity<Task>{
    private String className;
    private Date lastStartTime;
    private Boolean lastStartStatus;
    private String lastFailMsg;
}
