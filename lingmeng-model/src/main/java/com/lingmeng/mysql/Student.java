package com.lingmeng.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
@TableName("tt_student")
@Data
public class Student  {
    /**  */
    private String sId ;
    /**  */
    private String sName ;
    /**  */
    private Date sAge ;
    /**  */
    private String sSex ;
}
