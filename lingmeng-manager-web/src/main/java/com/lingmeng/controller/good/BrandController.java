package com.lingmeng.controller.good;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.api.good.IbrandService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.BrandMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.model.goods.model.Brand;
import com.lingmeng.model.goods.model.Category;
import com.lingmeng.model.goods.vo.req.BrandListReq;
import com.lingmeng.model.goods.vo.req.BrandReq;
import com.lingmeng.model.goods.vo.res.BrandRes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Component
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private IbrandService brandService;

    /**
     * @Author skin
     * @Date 2020/9/24
     * @Description 新增品牌
     **/
    @PostMapping("/add")
    @Transactional
    public RestReturn addBrand(@RequestBody BrandReq req) {
        Boolean result = brandMapper.selectList(new QueryWrapper<Brand>().eq("brand_name", req.getBrandName())).size() > 0;
        if (result) {
            throw new RestException("存在相同名字的品牌,请重新输入品牌名字");
        }
        Brand newBrand = new Brand();
        BeanUtils.copyProperties(req, newBrand);
        brandMapper.insert(newBrand);
        for (String i : req.getCategories()) {
            brandMapper.insertIntoCateAndBrand(i, newBrand.getId());
        }
        return RestReturn.ok("新增成功");
    }

    /**
     * @Author skin
     * @Date 2020/9/24
     * @Description 品牌列表
     **/
    @PostMapping("/list")
    public RestReturn brandList(@RequestBody @Valid BrandListReq req) {
        Page page = new Page<>();
        page.setCurrent(req.getPageNo());
        page.setSize(req.getPageSize());
        //得到当前分页数量
        List<BrandRes> brandList = brandService.getBrandList(req, page);
        //得到总数量--前端来分页
        Long count = brandMapper.getCount(req);
        page.setRecords(brandList);
        page.setTotal(count);
        return RestReturn.ok(page);
    }

    /**
     * @Author skin
     * @Date 2020/9/28
     * @Description 通过父组件获得属于哪些分类, 因为在父组件上获取不到, 需要子组件调用接口
     **/
    @GetMapping("/getOneById")
    public RestReturn getOneById(@RequestParam String brandId) {
        List<Category> categories = brandMapper.selectCategoryByBrandId(brandId);
        return RestReturn.ok(categories);
    }

    /**
     * @Author skin
     * @Date 2020/9/28
     * @Description 修改保存
     **/
    @PostMapping("/update")
    @Transactional
    public RestReturn update(@RequestBody BrandReq req) {
        Brand brand = brandMapper.selectById(req.getId());
        if (StringUtils.isEmpty(brand)) {
            throw new RestException("该品牌不存在");
        }
        brand.setBrandName(req.getBrandName());
        brand.setBrandLetter(req.getBrandLetter());
        brand.setBrandPic(req.getBrandPic());
        //先删除关联表的数据
        brandMapper.deleteByBrandId(req.getId());
        for (String id : req.getCategories()) {
            //插入最新的关系
            brandMapper.insertIntoCateAndBrand(id, req.getId());
        }
        brandMapper.updateById(brand);
        return RestReturn.ok("编辑成功");
    }
     /**
      * @Author skin
      * @Date  2020/9/28
      * @Description  删除品牌
     **/
    @GetMapping("/del")
    public RestReturn deleteById(@RequestParam String id) {
        Brand brand = brandMapper.selectById(id);
        if (StringUtils.isEmpty(brand)) {
            throw new RestException("该品牌不存在");
        }
        //如果直接删除掉的话,数据全部被删除,逻辑删除
        brandMapper.deleteById(id);
        return RestReturn.ok("删除成功");
    }


}
