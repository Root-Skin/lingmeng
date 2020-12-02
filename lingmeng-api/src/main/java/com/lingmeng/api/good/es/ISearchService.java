package com.lingmeng.api.good.es;


import com.lingmeng.page.PageResult;
import com.lingmeng.goods.model.es.Goods;
import com.lingmeng.goods.model.es.SearchRequest;
import com.lingmeng.goods.vo.res.SpuListRes;

public interface ISearchService {

     /**
      * @Author skin
      * @Date  2020/10/23
      * @Description 获取索性需要的相关信息
      **/
     Goods buildGoods(SpuListRes spu);


     /**
      * @Author skin
      * @Date  2020/10/23
      * @Description 通过es 搜索索引
      **/
     PageResult<Goods> esSearch(SearchRequest req);
      /**
       * @Author skin
       * @Date  2020/11/11
       * @Description 删除索引库对应商品的索引
       **/
     void  deleteIndex(String id);
      /**
       * @Author skin
       * @Date  2020/11/11
       * @Description 增,改 改动商品索引库中的索引
       **/
     void createIndex(String id);
}
