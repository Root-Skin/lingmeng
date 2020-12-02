package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.goods.model.SpuDetail;
import org.apache.ibatis.annotations.Param;


public interface SpuDetailMapper extends BaseMapper<SpuDetail> {


    SpuDetail getRelateInfoBySpuId(@Param("spuId") String spuId);
     /**
      * @Author skin
      * @Date  2020/10/21
      * @Description 根据spuid更新对应的spu
      **/
    Integer updateBySpuId (@Param("spuId") String spuId);
}
