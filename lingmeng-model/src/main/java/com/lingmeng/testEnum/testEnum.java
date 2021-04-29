package com.lingmeng.testEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
@Data
@TableName("lm_test")
public class testEnum {
    private  String id;
    private  ttEnum test2;
}
