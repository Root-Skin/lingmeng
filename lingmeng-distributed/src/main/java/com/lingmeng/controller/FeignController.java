package com.lingmeng.controller;

import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author skin
 * @createTime 2021年04月27日
 * @Description
 */
@RestController
@RequestMapping("/creaseStock")
public class FeignController {


     @Autowired
     private SkuStockMapper skuStockMapper;  //这里减少的另外一个数据库的库存数目

    /**
     * @author skin
     * @param miaoshaGoodsInRedis
     * @Date  2021-04-28 10:34
     * @description 分布式的情况下调用-->减少库存
     **/
    @RequestMapping("/crease")
    public RestReturn sendEmailCode(@RequestBody MiaoshaRedisDataVo miaoshaGoodsInRedis) {
        return  RestReturn.ok(skuStockMapper.SynchronizeToMysql(miaoshaGoodsInRedis));
    }
}
