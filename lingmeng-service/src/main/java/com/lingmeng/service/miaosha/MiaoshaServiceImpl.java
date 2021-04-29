package com.lingmeng.service.miaosha;


import com.alibaba.fastjson.JSONObject;
import com.lingmeng.api.miaosha.ImiaoshaService;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.dao.miaosha.MiaoshaOrderMapper;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class MiaoshaServiceImpl implements ImiaoshaService {




    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;

    @Autowired
    private SkuStockMapper skuStockMapper;


    @Override
    public List<MiaoshaRedisDataVo> getMiaoshaGoodsByTime(String time) {

        List<MiaoshaRedisDataVo> values = this.redisTemplate.boundHashOps("miaoshaGood_" + time).values();
        return values;
    }

    @Override
    public MiaoshaRedisDataVo getMiaoshaDetail(String time, String id) {
        //本身我们存储进去的就是map,所以取出来也是Map

        MiaoshaRedisDataVo miaoshaRedisDataVo = JSONObject.parseObject(this.redisTemplate.boundHashOps("miaoshaGood_" + time).get(id).toString(), MiaoshaRedisDataVo.class);
        return miaoshaRedisDataVo;
    }

}
