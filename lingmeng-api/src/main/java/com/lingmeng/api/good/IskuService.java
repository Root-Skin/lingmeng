package com.lingmeng.api.good;


import com.lingmeng.model.goods.model.Sku;

import java.util.List;

public interface IskuService {


    List<Sku> getRelateInfoBySpuId(String spuId);
}
