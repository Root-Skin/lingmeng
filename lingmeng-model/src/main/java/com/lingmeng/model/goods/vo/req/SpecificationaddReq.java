package com.lingmeng.model.goods.vo.req;

import lombok.Data;

@Data
public class SpecificationaddReq {



    /** 对应规格名称 */
    private String name;

    /** 是否通用 */
    private Boolean isGeneral;

    /** 是否数字类型 */
    private Boolean isNum;

    /** 单位 */
    private String unit;

    /** 是否搜索 */
    private Boolean isSearch;

    /** 区间起始 */
    private Integer  searchMin;
    /** 区间结束 */
    private Integer  searchMax;

}
