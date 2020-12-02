package com.lingmeng.service.goods;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.api.good.IspuService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.*;
import com.lingmeng.exception.RestException;
import com.lingmeng.goods.model.*;
import com.lingmeng.goods.vo.req.AddGoodReq;
import com.lingmeng.goods.vo.req.GoodListReq;
import com.lingmeng.goods.vo.res.SpuListRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SpuServiceImpl implements IspuService {
    private static final Logger logger = LoggerFactory.getLogger(SpuServiceImpl.class);


    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;
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
        saveSku(req.getSkus(), req.getId());
        sendMessage(req.getId(),"insert");
        return true;
    }

    @Override
    @Transactional
    public Boolean editGood(AddGoodReq req) {
        Spu spu = spuMapper.selectById(req.getId());
        if (StringUtils.isEmpty(spu)) {
            throw new RestException("该商品不存在,请重新选择");
        }
        spu.setTitle(req.getTitle());
        spu.setCid1(req.getCid1());
        spu.setCid2(req.getCid2());
        spu.setBrandId(req.getBrandId());
        spuMapper.updateById(spu);

        //spudetail
        SpuDetail relateInfoBySpuId = spuDetailMapper.getRelateInfoBySpuId(req.getId());

        String oldId = relateInfoBySpuId.getId();

        BeanUtils.copyProperties(req.getSpuDetail(),relateInfoBySpuId );
        relateInfoBySpuId.setId(oldId);
        spuDetailMapper.updateById(relateInfoBySpuId);
        //sku
        updateSku(req.getSkus(),req.getId());
        for (Sku sku : req.getSkus()) {
            System.out.println(sku.getStock());
        }
        sendMessage(req.getId(),"update");
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
        if (StringUtils.isEmpty(spu)) {
            throw new RestException("该商品不存在");
        }
        if (spu.getShelfFlag()) {
            spu.setShelfFlag(!spu.getShelfFlag());
            spuMapper.updateById(spu);
            return RestReturn.ok("商品下架成功");
        } else {
            spu.setShelfFlag(!spu.getShelfFlag());
            spuMapper.updateById(spu);
            return RestReturn.ok("商品上架成功");
        }
    }

    @Override
    public Map getGoodDetail(String spuId) {

        Spu spu = this.spuMapper.selectById(spuId);
        SpuDetail spuDetail = spuDetailMapper.getRelateInfoBySpuId(spuId);

        // 查询sku
        List<Sku> skus = this.skuMapper.getRelateInfoBySpuId(spuId);

        // 查询品牌()
        List<Brand> brands = this.brandMapper.queryNameByIds(Arrays.asList(spu.getBrandId()));
        // 查询分类
        List<Category> categories = this.categoryMapper.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2()));


        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", spu.getCid2()));

        List<String> allGroups = JSONArray.parseArray(specification.getSpecifications(), String.class);


        Map specialSpecificationMap = JSONObject.parseObject(spuDetail.getUniqueSpecification(), Map.class);
        //{颜色=["黑色"], 内存=["128","256"]}

        Map<String, Object> result = new HashMap<>();
        result.put("spu", spu);
        result.put("spuDetail", spuDetail);
        result.put("skus", skus);
        result.put("brand", brands.get(0));
        result.put("categories", categories);
        result.put("groups", allGroups);
        result.put("params", specialSpecificationMap);
        return result;

    }


    /**
     * @Author skin
     * @Date 2020/10/12
     * @Description 保存Sku相关联的信息
     **/
    private void saveSku(List<Sku> skus, String spuId) {

        for (Sku sku : skus) {
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

    private void updateSku(List<Sku> skus,String spuId) {

        for (Sku sku : skus) {
            System.out.println(sku);
            //默认为false
            if (!sku.getEnable()) {
                //跳出循环
                continue;
            }
            Sku oldSkuInfo = skuMapper.selectById(sku.getId());
            if(StringUtils.isEmpty(sku.getId())){
                Sku newSku  = new Sku();
                SkuStock newSkuStock = new SkuStock();
                BeanUtils.copyProperties(sku, newSku);
                newSku.setSpuId(spuId);
                skuMapper.insert(newSku);
                newSkuStock.setStock(sku.getStock());
                newSkuStock.setSkuId(newSku.getId());
                skuStockMapper.insert(newSkuStock);
            }else{

                BeanUtils.copyProperties(sku, oldSkuInfo);
                skuMapper.updateById(oldSkuInfo);
                SkuStock skuStock = skuStockMapper.selectBySkuId(sku.getId());
                System.out.println(sku.getStock());
                skuStock.setStock(sku.getStock());
                skuStockMapper.updateById(skuStock);
            }


        }

    }
     /**
      * @Author skin
      * @Date  2020/11/11
      * @Description 发送消息到MQ
      **/
     private void sendMessage(String id, String type){
         // 发送消息
         try {
             amqpTemplate.convertAndSend("lingmeng.good.exchange", "good."+type,id);
         } catch (AmqpException e) {
             logger.error("{}商品消息发送异常，商品id：{}", type, id, e);
         }
     }
}
