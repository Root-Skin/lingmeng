package com.lingmeng.controller.good;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.api.good.IskuService;
import com.lingmeng.api.good.IspuService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.CategoryMapper;
import com.lingmeng.dao.goods.SkuStockMapper;
import com.lingmeng.dao.goods.SpuDetailMapper;
import com.lingmeng.dao.goods.SpuMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.goods.model.Sku;
import com.lingmeng.goods.model.SkuStock;
import com.lingmeng.goods.model.Spu;
import com.lingmeng.goods.model.SpuDetail;
import com.lingmeng.goods.vo.req.AddGoodReq;
import com.lingmeng.goods.vo.req.GoodListReq;
import com.lingmeng.goods.vo.res.BrandRes;
import com.lingmeng.goods.vo.res.SpuListRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/good")
public class GoodController {
    private static final Logger logger = LoggerFactory.getLogger(GoodController.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IspuService spuService;

    @Autowired
    private IskuService skuService;

    @Autowired
    private SpuMapper spuMapper;


    @Autowired
    private SpuDetailMapper spuDetailMapper;


    @Autowired
    private SkuStockMapper skuStockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;


    /**
     * @Author skin
     * @Date 2020/9/30
     * @Description 根据分类ID得到该分类下有哪些具体的品牌
     **/
    @GetMapping("/getBrandsNameByCateId")
    public RestReturn getBrandsNameByCateId(@RequestParam String cateId) {
        List<BrandRes> brandsNameByCateId = categoryMapper.getBrandsNameByCateId(cateId);
        if (CollectionUtils.isEmpty(brandsNameByCateId)) {
            throw new RestException("该分类下不存在品牌信息,请重新选择");
        }
        return RestReturn.ok(brandsNameByCateId);
    }

    /**
     * @Author skin
     * @Date 2020/10/12
     * @Description 新增商品
     **/
    @PostMapping("/addNewGood")
    public RestReturn addNewGood(@RequestBody AddGoodReq req) {
        if(StringUtils.isEmpty(req.getId())){
            Boolean result = spuService.addNewGood(req);
            if (result) {
                return RestReturn.ok("保存成功");
            }
            return RestReturn.error("保存失败");
        }else{
            Boolean result = spuService.editGood(req);
            if (result) {
                return RestReturn.ok("编辑成功");
            }
            return RestReturn.error("编辑失败");
        }
        //如果id不为null-->编辑状态


    }

    /**
     * @Author skin
     * @Date 2020/10/12
     * @Description 获取商品列表
     **/
    @PostMapping("/goodsList")
    public RestReturn goodsList(@RequestBody @Valid GoodListReq req) {
        Page page = new Page<>();
        page.setCurrent(req.getPageNo());
        page.setSize(req.getPageSize());
        //得到当前分页数量
        List<SpuListRes> brandList = spuService.goodsList(req, page);
        //得到总数量--前端来分页
        Long count = spuMapper.getCount(req);
        page.setRecords(brandList);
        page.setTotal(count);
        return RestReturn.ok(page);
    }

    /**
     * @Author skin
     * @Date 2020/10/12
     * @Description 商品的上下架
     **/
    @GetMapping("/onAndoff")
    public RestReturn onAndoff(@RequestParam String spuId) {
        RestReturn restReturn = spuService.onAndoff(spuId);
        return restReturn;
    }

    /**
     * @Author skin
     * @Date 2020/10/12
     * @Description 商品修改查询
     **/
    @GetMapping("/spuUpdate")
    public RestReturn spuUpdate(@RequestParam String spuId) {
        Map result = new HashMap<>();
        Spu spu = spuMapper.selectById(spuId);
        if (StringUtils.isEmpty(spu)) {
            throw new RestException("该商品不存在");
        }
        //查询spuDetail
        SpuDetail relateDetail = spuDetailMapper.getRelateInfoBySpuId(spuId);
        List<Sku> relateSku = skuService.getRelateInfoBySpuId(spuId);
        for (Sku sku : relateSku) {
            SkuStock skuStock = skuStockMapper.selectBySkuId(sku.getId());
            sku.setStock(skuStock.getStock());
            sku.setSkuStockId(skuStock.getId());
        }
        result.put("spu", spu);
        result.put("spuDetail", relateDetail);
        result.put("relateSku", relateSku);
        return RestReturn.ok(result);
    }

    /**
     * @Author skin
     * @Date 2020/10/12
     * @Description 商品的删除(商品已经购买过不能够物理删除, 用户还要查看相关信息)
     **/
    @GetMapping("/spuDel")
    public RestReturn spuDel(@RequestParam String spuId) {
        Spu spu = spuMapper.selectById(spuId);
        if (StringUtils.isEmpty(spu)) {
            throw new RestException("该商品不存在");
        }
        spu.setDelFlag(true);
        spuMapper.updateById(spu);
        try {
            amqpTemplate.convertAndSend("lingmeng.good.exchange", "good.delete",spuId);
        } catch (AmqpException e) {
            logger.error("{}商品消息发送异常，商品id：{}", "delete", spuId, e);
        }
        return RestReturn.ok("删除成功");
    }

     /**
      * @Author skin
      * @Date  2020/11/9
      * @Description 组装商品详情页的数据
      **/
     @GetMapping("/GoodDetail")
     public RestReturn getGoodDetail(@RequestParam String spuId) {
         Map goodDetail = this.spuService.getGoodDetail(spuId);
         return RestReturn.ok(goodDetail);
     }

}
