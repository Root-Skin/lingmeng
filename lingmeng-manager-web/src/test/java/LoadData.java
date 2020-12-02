import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.LingmengManagerApplication;
import com.lingmeng.api.good.IspuService;
import com.lingmeng.api.good.es.GoodsRepository;
import com.lingmeng.api.good.es.ISearchService;
import com.lingmeng.goods.model.es.Goods;
import com.lingmeng.goods.vo.req.GoodListReq;
import com.lingmeng.goods.vo.res.SpuListRes;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class LoadData {


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private IspuService spuService;


    @Autowired
    private ISearchService iSearchService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void createIndex() {
        CreateIndexRequest request = new CreateIndexRequest("goods");//创建索引--和对应的映射关系
        request.mapping(JSONObject.toJSONString(Goods.class), XContentType.JSON);
        GoodListReq req = new GoodListReq();

        int pageNO = 1;
        int size = 0;
        Page page = new Page<>();
        page.setSize(100);

        do {
            // 查询分页数据
            page.setCurrent(pageNO);
            List<SpuListRes> result = spuService.goodsList(req, page);

            size = result.size();
            // 创建Goods集合
            List<Goods> goodsList = new ArrayList<>();
            // 遍历spu
            for (SpuListRes spu : result) {
                try {
                    Goods goods = this.iSearchService.buildGoods(spu);
                    goodsList.add(goods);
                } catch (Exception e) {
                    break;
                }
            }
            //将数据添加到索引库
            this.goodsRepository.saveAll(goodsList);
            pageNO++;
        } while (size == 100);
    }


}
