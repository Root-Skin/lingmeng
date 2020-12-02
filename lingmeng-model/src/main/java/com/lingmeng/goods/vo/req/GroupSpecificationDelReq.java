package com.lingmeng.goods.vo.req;

import lombok.Data;

@Data
public class GroupSpecificationDelReq {

    /** 对应分类id */
    private String id;


    /** 该规格对应分组的名称 */
    private String groupName;

    /** 原名称 */
    private String name;


}
