package com.lingmeng.goods.vo.req;

import lombok.Data;

@Data
public class SpecificationEditReq {

    /** 对应分类id */
    private String id;

    /** 原名称 */
    private String name;

    /** 新名称 */
    private String groupName;

}
