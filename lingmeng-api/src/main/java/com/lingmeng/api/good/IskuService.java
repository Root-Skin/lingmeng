package com.lingmeng.api.good;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.base.RestReturn;
import com.lingmeng.model.goods.vo.req.AddGoodReq;
import com.lingmeng.model.goods.vo.req.GoodListReq;
import com.lingmeng.model.goods.vo.res.SpuListRes;

import java.util.List;

public interface IspuService {

    Boolean addNewGood(AddGoodReq req);


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
}
