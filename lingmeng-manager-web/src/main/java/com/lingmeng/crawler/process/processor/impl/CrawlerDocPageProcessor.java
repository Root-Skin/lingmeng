package com.lingmeng.crawler.process.processor.impl;


import com.lingmeng.crawler.core.parse.ParseRule;
import com.lingmeng.crawler.enums.CrawlerEnum;
import com.lingmeng.crawler.helper.CrawlerHelper;
import com.lingmeng.crawler.process.entity.CrawlerConfigProperty;
import com.lingmeng.crawler.process.processor.AbstractCrawlerPageProcessor;
import com.lingmeng.crawler.utils.ParseRuleUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.List;

 /**
  * @Author skin
  * @Date  2021/1/1
  * @Description 将下载的数据最终解析出来并交给下一级解析处理器解析
  **/
@Component
@Log4j2
public class CrawlerDocPageProcessor extends AbstractCrawlerPageProcessor {

    @Autowired
    private CrawlerConfigProperty crawlerConfigProperty;

    @Autowired
    private CrawlerHelper crawlerHelper;

    /**
     * 处理页面数据
     * @param page
     */
    @Override
    public void handelPage(Page page) {
        long currentTimeMillis = System.currentTimeMillis();
        String handelType = crawlerHelper.getHandelType(page.getRequest());
        log.info("开始解析目标页数，url:{},handelType:{}",page.getUrl(),handelType);
        //获取目标页的抓取规则
        List<ParseRule> targetParseRuleList = crawlerConfigProperty.getTargetParseRuleList();
        //抽取有效的数据
        targetParseRuleList = ParseRuleUtils.parseHtmlByRuleList(page.getHtml(), targetParseRuleList);
        if(null != targetParseRuleList && !targetParseRuleList.isEmpty()){
            for(ParseRule parseRule : targetParseRuleList){
                //将数据添加到page中，交给后续的pipline处理
                log.info("添加数据字段到page中的field,url:{},handelType:{},field:{}",page.getUrl(),handelType,parseRule.getField());
                page.putField(parseRule.getField(),parseRule.getMergeContent());
            }
        }

        log.info("解析目标也数据完成，url:{},handelType:{},耗时:{}",page.getUrl(),handelType,System.currentTimeMillis()-currentTimeMillis);
    }

    @Override
    public boolean isNeedHandelType(String handelType) {
        return CrawlerEnum.HandelType.FORWARD.name().equals(handelType);
    }

    @Override
    public boolean isNeedDocumentType(String documentType) {
        return CrawlerEnum.DocumentType.PAGE.name().equals(documentType);
    }

    @Override
    public int getPriority() {
        return 120;
    }
}
