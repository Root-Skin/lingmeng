package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.goods.model.SkuStock;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import org.apache.ibatis.annotations.Param;


public interface SkuStockMapper extends BaseMapper<SkuStock> {


    SkuStock selectBySkuId(@Param("skuId") String skuId);

    /**
     * @Author skin
     * @Date  2020/12/11
     * @Description   After User Spike the sku Goods,Synchronize Data to Mysql
     **/
    Integer SynchronizeToMysql(@Param("miaoshaRedisDataVo") MiaoshaRedisDataVo miaoshaRedisDataVo);
}
