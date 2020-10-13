package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.model.goods.model.SpuDetail;
import org.apache.ibatis.annotations.Param;


public interface SpuDetailMapper extends BaseMapper<SpuDetail> {


    SpuDetail getRelateInfoBySpuId(@Param("spuId") String spuId);
}
