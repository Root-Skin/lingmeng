package com.lingmeng.controller.good;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.api.good.IcategoryService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.CategoryMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.goods.model.Category;
import com.lingmeng.goods.vo.req.CategoryReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private IcategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @Author skin
     * @Date 2020/8/21
     * @Description 根据父类ID查询子类项目
     **/
    @GetMapping("/list")
    public RestReturn getListByParentId(@RequestParam String pid) {
        List<Category> listByParentId = categoryService.getListByParentId(pid);
        return RestReturn.ok(listByParentId);
    }
     /**
      * @Author skin
      * @Date  2020/8/25
      * @Description     新增分类
      * //todo  需要检查有没有重复的数据
      **/
     @PostMapping("/add")
     public RestReturn addCategory(@RequestBody CategoryReq req) {
         Category category =new Category();
         BeanUtils.copyProperties(req,category);
         categoryMapper.insert(category);
         return RestReturn.ok("新增成功");
     }

      /**
       * @Author skin
       * @Date  2020/9/14
       * @Description 删除分类  (逻辑删除)
       **/
    @GetMapping("/del")
    public RestReturn addCategory(@RequestParam String  cateId) {
        Category category = categoryMapper.selectById(cateId);
        if(category==null){
            throw  new RestException("该分类不存在");
        }
        categoryMapper.deleteByCateId(cateId);
        return RestReturn.ok("删除成功");
    }

      /**
       * @Author skin
       * @Date  2020/8/25
       * @Description     修改分类(只能修改分类名称)
       **/
      @PostMapping("/update")
      public RestReturn updateCategory(@RequestBody CategoryReq req) {
          Category category = categoryMapper.selectById(req.getId());
          if (StringUtils.isEmpty(category)){
              throw  new  RestException("该对象不存在");
          }
          boolean result = categoryMapper.selectCount(new QueryWrapper<Category>().eq("category_name",req.getCategoryName()))>0;
          if(result){
              throw  new  RestException("分类名称出现重复");
          }
          category.setCategoryName(req.getCategoryName());
          categoryMapper.updateById(category);
          return RestReturn.ok("编辑成功");
      }



    /**
     * 根据商品分类id查询名称
     * @param ids 要查询的分类id集合
     * @return 多个名称的集合
     */
    @GetMapping("names")
    public RestReturn queryNameByIds(@RequestParam("ids") List<String> ids){

        List<Category> list = this.categoryMapper.queryNameByIds(ids);
        if (list == null || list.size() < 1) {
            return  RestReturn.error("未找到该ID集合下对应的名称");
        }
        return RestReturn.ok(list);
    }

     /**
      * @Author skin
      * @Date  2020/11/5
      * @Description 根据二级分类的接口查询一级和二级分类的名称
      **/
    @GetMapping("getAllCategoryName")
    public RestReturn getAllCategoryNameBySecondCateId(@RequestParam String secondCategoryId){
        List<Category> relateCategoryList = this.categoryService.getAllCategoryNameBySecondCateId(secondCategoryId);
        return RestReturn.ok(relateCategoryList);
    }
}
