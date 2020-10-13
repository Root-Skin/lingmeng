package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.model.goods.model.SkuStock;
import org.apache.ibatis.annotations.Param;


public interface SkuStockMapper extends BaseMapper<SkuStock> {


    SkuStock selectBySkuId(@Param("skuId") String skuId);
}
