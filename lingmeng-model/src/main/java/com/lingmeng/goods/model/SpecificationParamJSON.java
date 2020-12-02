package com.lingmeng.goods.model;

import lombok.Data;

import java.util.List;

@Data
public class SpecificationParamJSON {

    private Boolean isNum;

    private String unit;

    private Boolean isGeneral;

    private Boolean isSearch;

    private String name;


    private List<String> options;

    //详情上面多了一个具体的数值
    private String v;
    //搜索区间
    private String segments;


}
