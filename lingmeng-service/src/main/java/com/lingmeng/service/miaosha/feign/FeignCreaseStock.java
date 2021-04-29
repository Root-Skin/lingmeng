package com.lingmeng.service.miaosha.feign;

import com.lingmeng.base.RestReturn;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author skin
 * @createTime 2021年04月27日
 * @Description
 */

@FeignClient("lingmeng-distributed")
public interface FeignCreaseStock {

    @GetMapping("/creaseStock/crease")
    RestReturn creaseStock(@RequestBody MiaoshaRedisDataVo miaoshaRedisDataVo);
}
