package com.lingmeng.craw;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("ad_label")
@Data
public class AdLabel   extends SuperEntity  {
    private String name;
    private Boolean type;
}
