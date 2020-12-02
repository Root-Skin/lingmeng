package com.lingmeng.api.good;


import com.lingmeng.goods.model.Category;

import java.util.List;


public interface IcategoryService {

     /**
      * @Author skin
      * @Date  2020/8/21
      * @Description    根据父类的ID查找子列表
      **/
    List<Category> getListByParentId(String parentId) ;

     /**
      * @Author skin
      * @Date  2020/11/5
      * @Description 根据二级分类ID 查询 一级分类和二级分类的名称合计
      **/
    List<Category>getAllCategoryNameBySecondCateId(String secondCategoryId);
}
