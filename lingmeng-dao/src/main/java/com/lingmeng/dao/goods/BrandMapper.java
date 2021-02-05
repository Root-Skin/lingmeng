package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.goods.model.Brand;
import com.lingmeng.goods.model.Category;
import com.lingmeng.goods.vo.req.BrandListReq;
import com.lingmeng.goods.vo.res.BrandRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BrandMapper extends BaseMapper<Brand> {


    List<BrandRes> getBrandList(@Param("req") BrandListReq req, @Param("page") Page page);

    Long getCount(@Param("req")  BrandListReq req);
     /**
      * @Author skin
      * @Date  2020/9/25
      * @Description 对分类 品牌中间表插入数据
      **/
    Integer insertIntoCateAndBrand(@Param("cateId") String cateId,@Param("brandId") String brandId);


     /**
      * @Author skin
      * @Date  2020/9/28
      * @Description 根据父组件brandId 查询当前品牌的属于哪些分类
      **/
    List<Category> selectCategoryByBrandId(@Param("brandId") String brandId);
     /**
      * @Author skin
      * @Date  2020/9/28
      * @Description 根据brandID  删除关联表中的对应关系
      **/
     Integer deleteByBrandId(@Param("brandId") String brandId);


      /**
       * @Author skin
       * @Date  2020/10/26
       * @Description 根据ID批量查询 所有的名称
       **/
      List<Brand> queryNameByIds(@Param("ids")List<String> ids);


}
