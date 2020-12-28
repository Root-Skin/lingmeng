package com.lingmeng.service.es;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lingmeng.api.good.IskuService;
import com.lingmeng.api.good.es.GoodsRepository;
import com.lingmeng.api.good.es.ISearchService;
import com.lingmeng.dao.goods.*;
import com.lingmeng.goods.model.*;
import com.lingmeng.goods.model.es.Goods;
import com.lingmeng.goods.model.es.SearchRequest;
import com.lingmeng.goods.model.es.SearchResult;
import com.lingmeng.goods.vo.res.SpuListRes;
import com.lingmeng.page.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class EsSearchServiceImpl implements ISearchService {
    private static final Logger logger = LoggerFactory.getLogger(EsSearchServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IskuService skuService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private GoodsRepository goodsRepository;


    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Transactional
    public Goods buildGoods(SpuListRes req) {
        Goods newGoods = new Goods();

        Spu spu = spuMapper.selectById(req.getId());
        //String 转换为long
        List<Category> categoryNames = categoryMapper.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2()));
        //查询某一个spu下的具体sku
        List<Sku> relateSkus = skuService.getRelateInfoBySpuId(spu.getId());
        //查询详情(作用不影响主表的查询效率)
        SpuDetail spuDetail = spuDetailMapper.getRelateInfoBySpuId(spu.getId());

        //封装需要查询的sku信息
        List skuList = new ArrayList();
        //分装sku的的价格信息
        List<BigDecimal> prices = new ArrayList<>();

        relateSkus.forEach(sku -> {

            prices.add(sku.getPrice());

            Map result = new HashMap<>();
            result.put("id", sku.getId());
            result.put("price", sku.getPrice());
            result.put("title", sku.getTitle());
            result.put("image", sku.getImages());
            String s = JSONObject.toJSONString(result);
            skuList.add(s);
        });


        //普通规格
        List<SpecificationParamJSON> odinarySpecification = JSONArray.parseArray(spuDetail.getSpecifications(), SpecificationParamJSON.class);
        //特殊规格
        Map unique_specification = JSONObject.parseObject(spuDetail.getUniqueSpecification(), Map.class);

        //具体分类下的所有规格
        Specification cateSpecification = specificationMapper.getSpecificationByCateId(spu.getCid2());
        List<String> strings = JSONArray.parseArray(cateSpecification.getSpecifications(), String.class);
        //定义包装充当可搜索参数的map
        Map searchSpec = new HashMap<>();
        for (String s : strings) {
            JSONObject jsonObject = JSONObject.parseObject(s);
            System.out.println(jsonObject);
            List<JSONObject> params = JSONArray.parseArray(JSONObject.toJSONString(jsonObject.get("param")), JSONObject.class);
            for (JSONObject param : params) {
                //可以搜索
                if ((Boolean) param.get("isSearch")) {
                    //通用规格
                    if ((Boolean) param.get("isGeneral")) {
                        //得到通用规格下面的的具体值和对应的范围区间(通过specification中的name和detail中的名字进行对应)
                        for (SpecificationParamJSON e : odinarySpecification) {
                            if (e.getName().equals(param.get("name").toString())) {
                                String value = e.getV();
                                String name = e.getName();
                                String segments = e.getSegments();
                                String unit = e.getUnit();
                                if ((Boolean) param.get("isNum")) {
                                    value = chooseSegment(value, segments, unit);
                                }
                                searchSpec.put(name, StringUtils.isBlank(value) ? "其它" : value);
                            }
                        }
                    } else {
                        //这里特有规格的value应该从unique_specification解析出来
                        searchSpec.put(param.get("name").toString(), unique_specification.get(param.get("name").toString()));
                    }
                }
            }
        }
        newGoods.setId(spu.getId());
        newGoods.setSubTitle(spu.getTitle());
        newGoods.setBrandId(spu.getBrandId());
        newGoods.setCid1(spu.getCid1());
        newGoods.setCid2(spu.getCid2());
        newGoods.setId(spu.getId());
        newGoods.setAll(spu.getTitle() + " " + StringUtils.join(categoryNames, " "));
        newGoods.setPrice(prices);
        newGoods.setSkus(skuList.toString());
        newGoods.setSpecs(searchSpec);

        return newGoods;
    }

    //得到范围区间
    private String chooseSegment(String value, String segments, String unit) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        //将可选值分离出来
        for (String segment : segments.split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + unit + "以上";
                } else if (begin == 0) {
                    result = segs[1] + unit + "以下";
                } else {
                    result = segment + unit;
                }
                break;
            }
        }
        return result;
    }


    @Override
    public PageResult<Goods> esSearch(SearchRequest req) {
        String key = req.getKey();
        // 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品
        if (StringUtils.isBlank(key)) {
            return null;
        }


        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        QueryBuilder query = buildBasicQueryWithFilter(req);

//        MatchQueryBuilder basicQuery = QueryBuilders.matchQuery("all", key).operator(Operator.AND);

        // 1、对key进行全文检索查询
        queryBuilder.withQuery(query);

        // 2、通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle


        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id", "skus", "subTitle"}, null));

        //设置分页
        searchWithPageAndSort(queryBuilder, req);
        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");

//        highlightBuilder.fields().add(new HighlightBuilder.Field("subTitle"));
        queryBuilder.withHighlightBuilder(new HighlightBuilder().field("subTitle").preTags("<em>").postTags("</em>"));
        //添加聚合(field 表示划分桶的字段)
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brandId"));
        queryBuilder.addAggregation(AggregationBuilders.terms("category").field("cid2"));

        // 执行查询获取结果集
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        // 3、解析查询结果
        // 3.1、分页信息
        Long total = goodsPage.getTotalElements();
        int totalPage = (total.intValue() + req.getSize() - 1) / req.getSize();


        // 3.2、商品分类的聚合结果
        // 商品分类的聚合结果
        List<Category> categories =
                getCategoryAggResult(goodsPage.getAggregation("category"));
        // 品牌的聚合结果
        List<Brand> brands = getBrandAggResult(goodsPage.getAggregation("brands"));

        List<Map<String, Object>> specs = new ArrayList<>();
        //如果分类只有一个,继续进行过滤
        if (categories.size() == 1) {
            // 如果商品分类只有一个才进行聚合，并根据分类与基本查询条件聚合
            specs = getSpec(categories.get(0).getId(), query);
        }

        return new SearchResult(goodsPage.getTotalElements(), goodsPage.getTotalPages(), goodsPage.getContent(), categories, brands, specs);
    }


    /**
     * @Author skin
     * @Date 2020/10/26
     * @Description 分页和排序方法体
     **/
    private void searchWithPageAndSort(NativeSearchQueryBuilder queryBuilder, SearchRequest req) {
        // 准备分页参数
        int page = req.getPage();
        int size = req.getSize();
        // 1、分页
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 2、排序
        String sortBy = req.getSortBy();
        Boolean descending = req.getDescending();
        if (StringUtils.isNotBlank(sortBy)) {
            // 如果不为空，则进行排序
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(descending ? SortOrder.DESC : SortOrder.ASC));
        }
    }

    // 解析商品分类聚合结果
    private List<Category> getCategoryAggResult(Aggregation aggregation) {
        try {
            List<Category> categories = new ArrayList<>();
            Terms categoryAgg = (Terms) aggregation;
            List<String> cids = new ArrayList<>();
            for (Terms.Bucket bucket : categoryAgg.getBuckets()) {
                cids.add(bucket.getKeyAsString());
            }

            List<Category> names = categoryMapper.queryNameByIds(cids);
            // 根据id查询分类名称
            for (int i = 0; i < names.size(); i++) {
                Category c = new Category();
                c.setId(names.get(i).getId());
                c.setCategoryName(names.get(i).getCategoryName());
                categories.add(c);
            }
            return categories;
        } catch (Exception e) {
            logger.error("分类聚合出现异常：", e);
            return null;
        }
    }

    /**
     * @Author skin
     * @Date 2020/11/3
     * @Description 解析品牌聚合结果
     **/
    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        try {
            Terms brandAgg = (Terms) aggregation;
            List<String> bids = new ArrayList<>();
            for (Terms.Bucket bucket : brandAgg.getBuckets()) {
                bids.add(bucket.getKeyAsString());
            }
            // 根据id查询品牌
            return this.brandMapper.queryNameByIds(bids);
        } catch (Exception e) {
            logger.error("品牌聚合出现异常：", e);
            return null;
        }
    }

    /**
     * 聚合出规格参数
     *
     * @param cid
     * @param query
     * @return
     */
    private List<Map<String, Object>> getSpec(String cid, QueryBuilder query) {
        try {

            //优化方案,因为用户可以不断的切换,没有必要点击一次查询一次,但是es自己有缓存
            Specification specificationByCateId = specificationMapper.getSpecificationByCateId(cid);
//            Object o = redisTemplate.opsForValue().get(CacheKey.SPEC_CODE);
//            if (o==null) {
//                this.redisTemplate.opsForValue().set(CacheKey.SPEC_CODE,JSONObject.toJSON(specificationByCateId),5,TimeUnit.MINUTES);
//            }else{
//                Specification redis_specification = JSONObject.parseObject(JSONObject.toJSONString(o), Specification.class);
//                specificationByCateId = redis_specification;
//            }


            List<Map<String, Object>> specs = new ArrayList<>();

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

            queryBuilder.withQuery(query);


            /**
             * @Author skin
             * @Date 2020/11/4
             * @Description 这里必须要进行添加, 不然后续查询出来的是null指针   ,因为后续涉及到数组聚合查询.specs 无法获取
             **/
            queryBuilder.withSourceFilter(new FetchSourceFilter(
                    new String[]{"id", "subTitle"}, null));


            List<String> strings = JSONArray.parseArray(specificationByCateId.getSpecifications(), String.class);
            //定义包装充当可搜索参数的map

            List<JSONObject> params = new ArrayList<>();

            for (String s : strings) {
                JSONObject jsonObject = JSONObject.parseObject(s);
                params = JSONArray.parseArray(JSONObject.toJSONString(jsonObject.get("param")), JSONObject.class);
                for (JSONObject param : params) {
                    //可以搜索
                    if ((Boolean) param.get("isSearch")) {
                        String key = (String) param.get("name");
                        queryBuilder.addAggregation(AggregationBuilders.terms(key).field("specs." + key + ".keyword"));

                        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

                        Long total = goodsPage.getTotalElements();

                        Aggregation aggregation = goodsPage.getAggregation(key);


                        Map<String, Object> spec = new HashMap<>();
                        spec.put("k", key);
                        Terms terms = (Terms) aggregation;
                        spec.put("options", terms.getBuckets().stream().map(Terms.Bucket::getKeyAsString));
                        specs.add(spec);
                    }
                }
            }
            return specs;
        } catch (
                Exception e) {
            logger.error("规格聚合出现异常：", e);
            return null;
        }

    }


    /**
     * @Author skin
     * @Date 2020/11/4
     * @Description 构建基本查询条件
     **/
    private QueryBuilder buildBasicQueryWithFilter(SearchRequest request) {
        // BooleanQuery
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 基本查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));
        // 过滤条件构建器
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        // 整理过滤条件
        Map<String, String> filter = request.getFilter();
        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 商品分类和品牌要特殊处理
            if (key != "cid2" && key != "brandId") {
                key = "specs." + key + ".keyword";
            }
            //term查询 查询表示精确匹配查询
            //must表示可以不分词,然而filter不能使用分词
            filterQueryBuilder.must(QueryBuilders.termQuery(key, value));

        }
        // 添加过滤条件
        queryBuilder.filter(filterQueryBuilder);
        return queryBuilder;
    }

    /**
     * @Author skin
     * @Date 2020/11/11
     * @Description 创建索引
     **/
    public void createIndex(String id) {

        Spu spu = this.spuMapper.selectById(id);
        List<Brand> brands = brandMapper.queryNameByIds(Arrays.asList(spu.getBrandId()));
        List<Category> categories = categoryMapper.queryNameByIds((Arrays.asList(spu.getCid2())));

        SpuListRes res = new SpuListRes();
        res.setId(spu.getId());
        res.setBrandName(brands.get(0).getBrandName());
        res.setCategoryName(categories.get(0).getCategoryName());
        res.setCid1(spu.getCid1());
        res.setCid2(spu.getCid2());
        res.setTitle(spu.getTitle());
        // 构建商品
        Goods goods = this.buildGoods(res);

        // 保存数据到索引库
        this.goodsRepository.save(goods);


    }

    /**
     * @Author skin
     * @Date 2020/11/11
     * @Description 删除商品索引
     **/
    public void deleteIndex(String id) {
        this.goodsRepository.deleteById(id);
    }
}
