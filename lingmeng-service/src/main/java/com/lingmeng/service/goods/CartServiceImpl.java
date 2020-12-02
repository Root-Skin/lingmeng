package com.lingmeng.service.goods;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lingmeng.api.good.IcartService;
import com.lingmeng.common.Interceptor.LoginInterceptor;
import com.lingmeng.common.utils.auth.UserInfo;
import com.lingmeng.dao.goods.SkuMapper;
import com.lingmeng.goods.model.Sku;
import com.lingmeng.goods.vo.req.CartReq;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CartServiceImpl implements IcartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuMapper skuMapper;

    static final String KEY_PREFIX = "cart:userId:";
    static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public void addCart(CartReq req) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        // Redis的key
        String key = KEY_PREFIX + user.getId();


        // 获取hash操作对象
        //结构  redisTemplate.boundHashOps("hash").put("1", "a");
        //      redisTemplate.boundHashOps("hash").put("2", "b");
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        // 查询是否存在
        String skuId = req.getSkuId();
        Integer num = req.getNum();

        Boolean boo = hashOps.hasKey(skuId);
        if (boo) {
            // 存在，获取购物车数据
            String json = hashOps.get(skuId).toString();
            System.out.println(json);

            CartReq cartReq = JSON.parseObject(json, CartReq.class);
            // 修改购物车数量
            cartReq.setNum(cartReq.getNum() + num);

            hashOps.put(cartReq.getSkuId(), JSONObject.toJSONString(cartReq));
        } else {
            // 不存在，新增购物车数据
            req.setUserId(user.getId());
            // 其它商品信息， 需要查询商品服务
            Sku sku = skuMapper.selectById(skuId);
            req.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            req.setPrice(sku.getPrice());
            req.setTitle(sku.getTitle());
            req.setOwnSpec(sku.getOwnSpec());
            // 将购物车数据写入redis
            hashOps.put(req.getSkuId(), JSONObject.toJSONString(req));
        }


    }

    @Override
    public List<CartReq> queryCart() {
        //获取登陆用户
        UserInfo user = LoginInterceptor.getLoginUser();

        String key = KEY_PREFIX + user.getId();

        if (!this.redisTemplate.hasKey(key)) {
            // 不存在，直接返回
            return null;
        }
        //得到该用户下的key:value结构
        BoundHashOperations boundHashOperations = this.redisTemplate.boundHashOps(key);
        List<CartReq> values = boundHashOperations.values();
        // 判断是否有数据
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        System.out.println(values);
        String s = JSON.toJSONString(values);
        List result = new ArrayList();
        for (int i = 0; i < values.size(); i++) {
            System.out.println(values.get(i));
            CartReq cartReq = JSONObject.parseObject(String.valueOf(values.get(i)), CartReq.class);
            result.add(cartReq);
            System.out.println(cartReq);
        }
        //todo  这里必要要回顾一下
        // 查询购物车数据
//        return values.stream().map(o -> JSONObject.parseObject(JSON.toJSONString(o), CartReq.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void updateNum(String skuId, Integer num) {
        UserInfo user = LoginInterceptor.getLoginUser();

        String key = KEY_PREFIX + user.getId();
        BoundHashOperations boundHashOperations = this.redisTemplate.boundHashOps(key);
        String s = boundHashOperations.get(skuId).toString();
        CartReq cartReq = JSONObject.parseObject(s, CartReq.class);
        cartReq.setNum(num);
        boundHashOperations.put(skuId, JSONObject.toJSONString(cartReq));

    }

    @Override
    public void delete(String skuId) {
        UserInfo user = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations boundHashOperations = this.redisTemplate.boundHashOps(key);
        boundHashOperations.delete(skuId);
    }
}
