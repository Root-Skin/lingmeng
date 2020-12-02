package com.lingmeng.goods.model.es;

import com.lingmeng.goods.model.Brand;
import com.lingmeng.goods.model.Category;
import com.lingmeng.page.PageResult;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
  * @Author skin
  * @Date  2020/11/3
  * @Description 搜索需要的扩展类(扩展分类和品牌信息)
  **/
@Data
public class SearchResult extends PageResult<Goods> {





    private List<Category> categories;

    private List<Brand> brands;

      /**
       * @Author skin
       * @Date  2020/11/3
       * @Description 需要过滤的规格参数
       **/
     private List<Map<String,Object>> specs; // 规格参数过滤条件

     public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands,List<Map<String,Object>> specs) {
         super(total, totalPage, items);
         this.categories = categories;
         this.brands = brands;
         this.specs = specs;
     }


}
