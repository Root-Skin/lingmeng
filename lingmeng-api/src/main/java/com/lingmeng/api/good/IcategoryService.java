package com.lingmeng.api.good;


import com.lingmeng.model.goods.model.Category;

import java.util.List;


public interface IcategoryService {

     /**
      * @Author skin
      * @Date  2020/8/21
      * @Description    根据父类的ID查找子列表
      **/
    List<Category> getListByParentId(String parentId) ;
}
