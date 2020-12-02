package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.goods.model.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SkuMapper extends BaseMapper<Sku> {

    List<Sku> getRelateInfoBySpuId(@Param("spuId") String SpuId);
}
