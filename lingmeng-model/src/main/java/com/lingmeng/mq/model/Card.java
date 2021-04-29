package com.lingmeng.mq.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

import java.io.Serializable;

@TableName("lm_card")
@Data
public class Card extends SuperEntity  implements Serializable {


    private String cardName ;


}
