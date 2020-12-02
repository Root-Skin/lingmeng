package com.lingmeng.api.good;


import com.lingmeng.goods.vo.req.CartReq;

import java.util.List;

public interface IcartService {

  void  addCart(CartReq req);

   /**
    * @Author skin
    * @Date  2020/11/13
    * @Description 查询购物车
    **/
  List<CartReq>  queryCart();

   void updateNum(String skuId,Integer num);

    void delete(String skuId);
}
