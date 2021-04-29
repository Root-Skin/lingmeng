package com.lingmeng.api.miaosha;


import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;

import java.util.List;

public interface ImiaoshaService {

   List<MiaoshaRedisDataVo> getMiaoshaGoodsByTime(String time);

   MiaoshaRedisDataVo getMiaoshaDetail(String time,String id);

}
