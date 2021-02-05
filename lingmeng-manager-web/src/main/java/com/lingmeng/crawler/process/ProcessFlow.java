package com.lingmeng.crawler.process;

import com.lingmeng.crawler.enums.CrawlerEnum;
import com.lingmeng.crawler.process.entity.ProcessFlowData;

public interface ProcessFlow {
    /**
     * 处理主业务
     *
     * @param processFlowData
     */
    public void handel(ProcessFlowData processFlowData);

    /**
     * 获取抓取类型
     *
     * @return
     */
    public CrawlerEnum.ComponentType getComponentType();

    /**
     * 获取优先级
     * @return
     */
    public int getPriority();
}
