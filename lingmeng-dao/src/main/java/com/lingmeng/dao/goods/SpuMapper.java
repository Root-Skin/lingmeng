package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.goods.model.Spu;
import com.lingmeng.goods.vo.req.GoodListReq;
import com.lingmeng.goods.vo.res.SpuListRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SpuMapper extends BaseMapper<Spu> {

    List<SpuListRes> goodsList(@Param("req") GoodListReq req, @Param("page") Page page);


    Long getCount(@Param("req") GoodListReq req);

    Map getRelateInfoBySpuId(@Param("spuId") String spuId);


}
