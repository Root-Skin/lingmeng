package com.lingmeng.service.goods;


import com.lingmeng.api.good.IcategoryService;
import com.lingmeng.dao.goods.CategoryMapper;
import com.lingmeng.model.goods.model.Category;
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
