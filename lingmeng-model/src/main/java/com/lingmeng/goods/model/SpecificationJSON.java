package com.lingmeng.goods.model;

import lombok.Data;

import java.util.List;

@Data
public class SpecificationJSON {
    //所属分组
//   private String group;

   //分组参数
    private List<SpecificationParamJSON> param;


}
