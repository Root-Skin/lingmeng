package com.lingmeng.goods.service;


import com.lingmeng.goods.api.IcategoryService;
import com.lingmeng.goods.mapper.CategoryMapper;
import com.lingmeng.goods.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements IcategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> getListByParentId(String parentId) {
        List<Category> listByParentId = categoryMapper.getListByParentId(parentId);
        return listByParentId;
    }
}
