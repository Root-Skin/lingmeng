package com.lingmeng.goods.model.es;


import com.lingmeng.base.SuperEntity;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Document(indexName = "goods", shards = 1, replicas = 0)
@Data
public class Goods extends SuperEntity {

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all; // 所有需要被搜索的信息，包含标题，分类，甚至品牌
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;// 卖点
    @Field(type = FieldType.Keyword,fielddata=true ,index = false)
    private String brandId;// 品牌id
    private String cid1;// 1级分类id
    @Field(type = FieldType.Keyword, fielddata=true ,index = false)
    private String cid2;// 2级分类id
    private List<BigDecimal> price;// 价格
    @Field(type = FieldType.Keyword, index = false)
    private String skus;// sku信息的json结构
    @Field(type = FieldType.Keyword, fielddata=true ,index = false)
    private Map specs;// 可搜索的规格参数，key是参数名，值是参数值
}
