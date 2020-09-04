package com.lingmeng.goods.controller;


import com.lingmeng.goods.mapper.CategoryMapper;
import com.lingmeng.goods.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @Author skin
     * @Date 2020/8/21
     * @Description 根据父类ID查询子类项目
     **/
//    @GetMapping("/list")
//    public RestReturn<SuperEntity> getListByParentId(@RequestParam String pid) {
//        List<Category> listByParentId = categoryService.getListByParentId(pid);
//        return RestReturn.ok(listByParentId);
//    }
     /**
      * @Author skin
      * @Date  2020/8/25
      * @Description     新增分类
      **/
//     public RestReturn<SuperEntity> addCategory(@RequestBody CategoryReq req) {
//         Category category =new Category();
//         BeanUtils.copyProperties(req,category);
//         categoryMapper.insert(category);
//         return RestReturn.ok("新增成功");
//     }

      /**
       * @Author skin
       * @Date  2020/8/25
       * @Description     修改分类
       **/
//      public RestReturn<SuperEntity> updateCategory(@RequestBody CategoryReq req) {
//
//          Category category =new Category();
//          BeanUtils.copyProperties(req,category);
//          categoryMapper.insert(category);
//          return RestReturn.ok("新增成功");
//      }
}
