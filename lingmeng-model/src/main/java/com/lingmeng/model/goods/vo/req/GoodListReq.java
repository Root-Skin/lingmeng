package com.lingmeng.model.goods.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GoodListReq  {



    /** 页码 */
    @NotNull(message = "页码不能为空")
    private Long pageNo ;
    /** 每页数量 */
    @NotNull(message = "每页大小不能为空")
    private Long pageSize ;


    /** 关键字 */
    private String   keyWords ;

    private Boolean  shelfFlag;
}
