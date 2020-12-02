package com.lingmeng.controller.es;

import com.lingmeng.api.good.es.ISearchService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.page.PageResult;
import com.lingmeng.goods.model.es.Goods;
import com.lingmeng.goods.model.es.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es")
public class EsSearchController {
    @Autowired
    private ISearchService searchService;
    /**
     * 搜索商品
     *
     * @param request
     * @return
     */
    @PostMapping("/goodsPage")
    public RestReturn search(@RequestBody SearchRequest request) {
        PageResult<Goods> result = this.searchService.esSearch(request);
        if (result == null) {
            return RestReturn.error("没有搜索到对应商品");
        }
        return RestReturn.ok(result);
    }

}
