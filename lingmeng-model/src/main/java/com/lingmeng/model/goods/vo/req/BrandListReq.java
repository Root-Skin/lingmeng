package com.lingmeng.model.goods.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BrandListReq {


    /** 页码 */
    @NotNull(message = "页码不能为空")
    private Long pageNo ;
    /** 每页数量 */
    @NotNull(message = "每页大小不能为空")
    private Long pageSize ;

    /** 排序字段 */

    private String sortBy ;

    /** 是否升序 */
    private Boolean  desc ;

    /** 关键字 */
    private String   keyWords ;

}
