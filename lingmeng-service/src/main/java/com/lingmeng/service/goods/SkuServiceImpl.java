package com.lingmeng.service.goods;


import com.lingmeng.api.good.IskuService;
import com.lingmeng.dao.goods.SkuMapper;
import com.lingmeng.model.goods.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SkuServiceImpl implements IskuService {




    @Autowired
    private SkuMapper skuMapper;

    @Override
    public List<Sku> getRelateInfoBySpuId(String spuId) {
        List<Sku> relateInfoBySpuId = skuMapper.getRelateInfoBySpuId(spuId);
        return relateInfoBySpuId;
    }
}
