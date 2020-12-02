package com.lingmeng.goods.vo.req;

import lombok.Data;

import java.util.List;

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

    private String segments;


    /** 这是针对特有规格的选项 */
    private List options;




}
