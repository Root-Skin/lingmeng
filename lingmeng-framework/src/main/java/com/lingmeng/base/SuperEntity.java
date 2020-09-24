package com.lingmeng.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private Date createdTime ;
    /** 更新人 */
    private String updatedBy ;
    /** 更新时间 */
    private Date updatedTime ;
    /** 刪除 标识位*/
    private Boolean delFlag  =false;

}
