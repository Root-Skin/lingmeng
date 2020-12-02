package com.lingmeng.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SuperEntity {

    /**主键 */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id ;
    /** 创建人 */
    private String createdBy ;
    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd ", timezone="GMT+8")
    private Date createdTime ;
    /** 更新人 */
    private String updatedBy ;
    /** 更新时间 */
    @JsonFormat(pattern="yyyy-MM-dd ", timezone="GMT+8")
    private Date updatedTime ;
    /** 刪除 标识位*/
    private Boolean delFlag  =false;

}
