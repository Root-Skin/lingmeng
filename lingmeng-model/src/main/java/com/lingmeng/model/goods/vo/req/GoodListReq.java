package com.lingmeng.model.goods.vo.req;

import com.lingmeng.model.goods.model.Sku;
import com.lingmeng.model.goods.model.Spu;
import com.lingmeng.model.goods.model.SpuDetail;
import lombok.Data;

import java.util.List;

@Data
public class AddGoodReq extends Spu {


    String cname;// 商品分类名称

    String bname;// 品牌名称

    SpuDetail spuDetail;// 商品详情

    List<Sku> skus;// sku列表
}
