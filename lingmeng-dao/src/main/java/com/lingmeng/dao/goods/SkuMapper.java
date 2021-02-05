package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.goods.model.Sku;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;


public interface SkuMapper extends BaseMapper<Sku> {

    List<Sku> getRelateInfoBySpuId(@Param("spuId") String SpuId);


     /**
      * @Author skin
      * @Date  2020/12/9
      * @Description 查询出当前时间和当前时间+2小时后的可以秒杀的sku(并且排除redis中含有的数据)
      **/
    List<MiaoshaRedisDataVo> selectByMiaoshaTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("keys") Set keys);


}
