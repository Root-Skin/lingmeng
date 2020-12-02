package com.lingmeng.api.good.es;

import com.lingmeng.goods.model.es.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {
}
