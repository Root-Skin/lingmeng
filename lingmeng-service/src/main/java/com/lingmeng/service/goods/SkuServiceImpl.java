package com.lingmeng.service.goods;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.api.good.IspuService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.SkuMapper;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.dao.goods.SpuDetailMapper;
import com.lingmeng.dao.goods.SpuMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.model.goods.model.Sku;
import com.lingmeng.model.goods.model.SkuStock;
import com.lingmeng.model.goods.model.Spu;
import com.lingmeng.model.goods.vo.req.AddGoodReq;
import com.lingmeng.model.goods.vo.req.GoodListReq;
import com.lingmeng.model.goods.vo.res.SpuListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class SpuServiceImpl implements IspuService {


    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;

    @Override
    @Transactional
    public Boolean addNewGood(AddGoodReq req) {
        req.setShelfFlag(true);
        //因为是继承关系 可以insert
        spuMapper.insert(req);

        //插入后生成ID
        req.getSpuDetail().setSpuId(req.getId());
        spuDetailMapper.insert(req.getSpuDetail());
        //保存sku
        saveSku(req.getSkus(),req.getId());
        return true;
    }

    @Override
    public List<SpuListRes> goodsList(GoodListReq req, Page page) {
        List<SpuListRes> goodsList = spuMapper.goodsList(req, page);
        return goodsList;
    }

    @Override
    public RestReturn onAndoff(String spuId) {
        Spu spu = spuMapper.selectById(spuId);
        if(StringUtils.isEmpty(spu)){
            throw  new RestException("该商品不存在");
        }
        if(spu.getShelfFlag()){
            spu.setShelfFlag(!spu.getShelfFlag());
            spuMapper.updateById(spu);
            return RestReturn.ok("商品下架成功");
        }else{
            spu.setShelfFlag(!spu.getShelfFlag());
            spuMapper.updateById(spu);
            return RestReturn.ok("商品上架成功");
        }
    }


    /**
      * @Author skin
      * @Date  2020/10/12
      * @Description 保存Sku相关联的信息
      **/
    private void saveSku(List<Sku> skus,String spuId){

        for(Sku sku : skus){
            //默认为false
            if (!sku.getEnable()) {
                //跳出循环
                continue;
            }
            sku.setSpuId(spuId);
            skuMapper.insert(sku);
            SkuStock newSkuStock = new SkuStock();
            //插入后会获得相应的ID
            newSkuStock.setSkuId(sku.getId());
            skuStockMapper.insert(newSkuStock);
        }

    }
}
