package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.goods.model.Category;
import com.lingmeng.goods.vo.res.BrandRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CategoryMapper extends BaseMapper<Category> {
    @Select("SELECT *  FROM lm_category where parent_id = #{parentId}  AND del_flag = 0 ")
    List<Category> getListByParentId(String parentId);


    Boolean deleteByCateId(@Param("cateId")String cateId);

     /**
      * @Author skin
      * @Date  2020/9/30
      * @Description 根据分类ID得到该分类下有哪些具体的品牌
      **/
    List<BrandRes> getBrandsNameByCateId(@Param("cateId")String cateId);

    List<Category> queryNameByIds(@Param("ids")List<String> ids);
}
