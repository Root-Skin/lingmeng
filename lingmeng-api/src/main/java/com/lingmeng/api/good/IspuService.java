package com.lingmeng.api.good;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.base.RestReturn;
import com.lingmeng.goods.vo.req.AddGoodReq;
import com.lingmeng.goods.vo.req.GoodListReq;
import com.lingmeng.goods.vo.res.SpuListRes;

import java.util.List;
import java.util.Map;

public interface IspuService {

     /**
      * @Author skin
      * @Date  2020/10/21
      * @Description 新增商品
      **/
    Boolean addNewGood(AddGoodReq req);
     /**
      * @Author skin
      * @Date  2020/10/21
      * @Description 编辑商品
      **/

     Boolean editGood(AddGoodReq req);
     /**
      * @Author skin
      * @Date  2020/10/12
      * @Description  得到每一页的数据
      **/
    List<SpuListRes> goodsList(GoodListReq req, Page page);
     /**
      * @Author skin
      * @Date  2020/10/12
      * @Description 商品的上下架
      **/
     RestReturn onAndoff(String spuId);


      /**
       * @Author skin
       * @Date  2020/11/9
       * @Description 通过spuId组装详情需要展示的信息
       **/
    Map getGoodDetail(String spuId);
}
