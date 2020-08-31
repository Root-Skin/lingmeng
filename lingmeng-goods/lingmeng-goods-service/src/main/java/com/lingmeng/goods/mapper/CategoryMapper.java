package com.lingmeng.goods.mapper;

import com.lingmeng.goods.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface CategoryMapper extends Mapper<Category> {
    @Select("SELECT *  FROM lm_category where parent_id = #{parentId}")
    List<Category> getListByParentId(String parentId);
}
