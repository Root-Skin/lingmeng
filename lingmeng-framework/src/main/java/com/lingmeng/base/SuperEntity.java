package com.lingmeng.base;

import com.lingmeng.base.lingmengPlug.AutoId;
import lombok.Data;

import java.util.Date;

@Data
public class SuperEntity {

    /**主键 */
    @AutoId
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
    private boolean delFlag ;

}
