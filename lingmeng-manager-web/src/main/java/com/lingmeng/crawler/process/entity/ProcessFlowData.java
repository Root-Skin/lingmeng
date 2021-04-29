package com.lingmeng.crawler.process.entity;

import com.lingmeng.crawler.core.parse.ParseItem;
import com.lingmeng.crawler.enums.CrawlerEnum;
import lombok.Data;

import java.util.List;

/**
 * 流程数据
 */
@Data
public class ProcessFlowData {
    /**
     * 抓取对象列表
     */
    private List<ParseItem> parseItemList;

    /**
     * 处理类型(正向处理)
     */
    private CrawlerEnum.HandelType handelType = CrawlerEnum.HandelType.FORWARD;



}
