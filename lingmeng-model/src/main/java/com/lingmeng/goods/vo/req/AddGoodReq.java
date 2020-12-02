package com.lingmeng.goods.vo.req;

import com.lingmeng.goods.model.Sku;
import com.lingmeng.goods.model.Spu;
import com.lingmeng.goods.model.SpuDetail;
import lombok.Data;

import java.util.List;

@Data
public class AddGoodReq extends Spu {


    String cname;// 商品分类名称

    String bname;// 品牌名称

    SpuDetail spuDetail;// 商品详情

    List<Sku> skus;// sku列表
}
