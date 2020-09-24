package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.model.goods.model.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CategoryMapper extends BaseMapper<Category> {
    @Select("SELECT *  FROM lm_category where parent_id = #{parentId}  AND del_flag = 0 ")
    List<Category> getListByParentId(String parentId);


    Boolean deleteByCateId(@Param("cateId")String cateId);
}
