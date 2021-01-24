package com.lingmeng.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SuperEntity<T extends Model> extends Model{

    /**主键 */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id ;
    /** 创建人 */
    private String createdBy ;
    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd ", timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime ;
    /** 更新人 */
    private String updatedBy ;
    /** 更新时间 */
    @JsonFormat(pattern="yyyy-MM-dd ", timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime ;
    /** 刪除 标识位*/
    private Boolean delFlag  =false;



    //返回当前类的主键
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
