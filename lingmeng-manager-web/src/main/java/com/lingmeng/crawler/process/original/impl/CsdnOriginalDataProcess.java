package com.lingmeng.crawler.process.original.impl;


import com.lingmeng.crawler.config.CrawlerConfig;
import com.lingmeng.crawler.core.parse.ParseItem;
import com.lingmeng.crawler.core.parse.impl.CrawlerParseItem;
import com.lingmeng.crawler.enums.CrawlerEnum;
import com.lingmeng.crawler.process.entity.ProcessFlowData;
import com.lingmeng.crawler.process.original.AbstractOriginalDataProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CsdnOriginalDataProcess extends AbstractOriginalDataProcess {

    @Autowired
    private CrawlerConfig crawlerConfig;

     /**
      * @Author skin
      * @Date  2020/12/31
      * @Description 1.解析初始数据
      **/
    @Override
    public List<ParseItem> parseOriginalRequestData(ProcessFlowData processFlowData) {
        List<ParseItem> parseItemList = null;
        List<String> initCrawlerUrlList = crawlerConfig.getInitCrawlerUrlList();

        if(!CollectionUtils.isEmpty(initCrawlerUrlList)){
            //stream().map的使用将List<Stirng>-->转换为List<ParseItem>(处理过程中改变类型)
            parseItemList = initCrawlerUrlList.stream().map(url->{
                CrawlerParseItem parseItem = new CrawlerParseItem();
                url = url+"?rnd="+System.currentTimeMillis();
                parseItem.setUrl(url);
                //抓取类型(INIT 初始化类型)
                parseItem.setDocumentType(CrawlerEnum.DocumentType.INIT.name());
                //处理类型类型(正向处理)
                parseItem.setHandelType(processFlowData.getHandelType().name());

                log.info("初始化URL:{}",url);
                return parseItem;
            }).collect(Collectors.toList());
        }
        return parseItemList;
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
