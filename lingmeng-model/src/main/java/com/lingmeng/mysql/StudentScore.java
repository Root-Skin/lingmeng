package com.lingmeng.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
@TableName("tt_sc")
@Data
public class StudentScore extends SuperEntity {
    /**  */
    private String sId ;
    /**  */
    private String cId ;
    /**  */
    private BigDecimal score ;
}
