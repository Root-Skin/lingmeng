package com.lingmeng.craw;

import com.lingmeng.base.SuperEntity;
import lombok.Data;

@Data
public class AdChannelLabel  extends SuperEntity {
    private Integer channelId;
    private Integer labelId;
    private Integer ord;
}
