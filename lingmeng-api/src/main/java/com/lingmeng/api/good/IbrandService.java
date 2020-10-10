package com.lingmeng.api.good;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.model.goods.vo.req.BrandListReq;
import com.lingmeng.model.goods.vo.res.BrandRes;

import java.util.List;

public interface IbrandService {

     List<BrandRes> getBrandList(BrandListReq req, Page page);
}
