package com.lingmeng.service.goods;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingmeng.api.good.IskuStockService;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.goods.model.SkuStock;
import org.springframework.stereotype.Service;


@Service
public class SkuStockServiceImpl extends ServiceImpl<SkuStockMapper, SkuStock> implements IskuStockService {


}
