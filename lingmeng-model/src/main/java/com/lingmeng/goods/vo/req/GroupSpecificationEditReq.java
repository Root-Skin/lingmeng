package com.lingmeng.goods.vo.req;

import lombok.Data;

import java.util.List;

@Data
public class GroupSpecificationEditReq {

    /** 对应分类id */
    private String id;

    /** 该规格对应分组的名称 */
    private String groupName;

    /** 原名称 */
    private String oldName;

    /** 新名称 */
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
    private List options;
    private String segments;

}
