package com.lingmeng.service.goods;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.api.good.IbrandService;
import com.lingmeng.dao.goods.BrandMapper;
import com.lingmeng.model.goods.vo.req.BrandListReq;
import com.lingmeng.model.goods.vo.res.BrandRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandServiceImpl implements IbrandService {


    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<BrandRes> getBrandList(BrandListReq req, Page page) {
        List<BrandRes> brandList = brandMapper.getBrandList(req, page);
        return brandList;
    }
}
