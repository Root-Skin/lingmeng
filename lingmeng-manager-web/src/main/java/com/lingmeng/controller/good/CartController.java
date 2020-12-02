package com.lingmeng.controller.good;

import com.lingmeng.api.good.IcartService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.goods.vo.req.CartReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private IcartService cartService;


     /**
      * @Author skin
      * @Date  2020/11/13
      * @Description  用户登陆情况下  向购物车添加商品
      **/
    @PostMapping("/add")
    public RestReturn addCart(@RequestBody CartReq cart) {
        this.cartService.addCart(cart);
        return RestReturn.ok();
    }

     /**
      * @Author skin
      * @Date  2020/11/13
      * @Description 用户登陆情况下 ,查询购物车
      **/
    @GetMapping("/queryCart")
    public RestReturn queryCart() {
        List<CartReq> cartReqs = this.cartService.queryCart();
        return RestReturn.ok(cartReqs);
    }

     /**
      * @Author skin
      * @Date  2020/11/13
      * @Description 用户登陆修改商品数量
      **/
     @GetMapping("/updateNum")
     public RestReturn updateNum(@RequestParam String skuId,@RequestParam Integer num) {
         this.cartService.updateNum(skuId,num);
         return RestReturn.ok();
     }
      /**
       * @Author skin
       * @Date  2020/11/13
       * @Description 用户登陆删除对应商品
       **/
      @GetMapping("/delete")
      public RestReturn delete(@RequestParam String skuId) {
          this.cartService.delete(skuId);
          return RestReturn.ok();
      }
}
