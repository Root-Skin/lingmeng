package com.lingmeng.model.goods.vo.res;

import lombok.Data;

@Data
public class AccurateSpecificationRes {


    /** 对应分类的id */
    private String id;
    /** 对应分类名称 */
    private String name;

    /** 是否为数值 */
    private Boolean IsNum;

    /** 单位 */
    private String unit;

    /** 是否通用 */
    private Boolean Isgeneral;

    /** 是否可搜索 */
    private Boolean IsSearch;
}
