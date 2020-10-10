package com.lingmeng.controller.good;

import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.CategoryMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.model.goods.vo.res.BrandRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/getBrandsNameByCateId")
    public RestReturn getBrandsNameByCateId(@RequestParam String cateId) {
        List<BrandRes> brandsNameByCateId = categoryMapper.getBrandsNameByCateId(cateId);
        if(CollectionUtils.isEmpty(brandsNameByCateId)){
            throw  new RestException("该分类下不存在品牌信息,请重新选择");
        }
        return  RestReturn.ok(brandsNameByCateId);
    }
}
