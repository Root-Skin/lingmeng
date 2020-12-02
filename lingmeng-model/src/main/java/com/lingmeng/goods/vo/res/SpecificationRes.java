package com.lingmeng.goods.vo.res;

import lombok.Data;

import java.util.List;

@Data
public class SpecificationRes {

    /** 对应分类名称 */
    private String categoryId;


    /** 分组名称 */
    private List groupName;

}
