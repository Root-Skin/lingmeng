package com.lingmeng.crawler.process.processor;


import com.lingmeng.crawler.helper.CrawlerHelper;
import com.lingmeng.crawler.process.ProcessFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * 爬虫文档流程管理(文档处理管理器 将三种文档处理器 结合起来进行有序的处理，从初始化URL 解析到帮助页 以及最终URL的解析)
 */
@Component
public class CrawlerPageProcessorManager {

    @Autowired
    private CrawlerHelper crawlerHelper;

    //注入继承了抽象类AbstractCrawlerPageProcessor 的的所有类
    @Resource
    private List<AbstractCrawlerPageProcessor>  abstractCrawlerPageProcessorList;

    /**
     * 初始化注入 接口顺序的方法
     */
    @PostConstruct
    public void initProcessingFlow(){
        if(abstractCrawlerPageProcessorList!=null && !abstractCrawlerPageProcessorList.isEmpty()){
            abstractCrawlerPageProcessorList.sort(new Comparator<ProcessFlow>() {
                @Override
                public int compare(ProcessFlow o1, ProcessFlow o2) {
                    if(o1.getPriority() > o2.getPriority()){
                        return 1;
                    }else if(o1.getPriority() > o2.getPriority()){
                        return -1;
                    }
                    return 0;
                }
            });
        }
    }

    /**
     * 处理数据
     * @param page
     */
    public void handel(Page page){

        String handelType = crawlerHelper.getHandelType(page.getRequest());
        String documentType = crawlerHelper.getDocumentType(page.getRequest());

        for (AbstractCrawlerPageProcessor abstractCrawlerPageProcessor : abstractCrawlerPageProcessorList) {
            boolean needHandelType = abstractCrawlerPageProcessor.isNeedHandelType(handelType);
            boolean needDocumentType = abstractCrawlerPageProcessor.isNeedDocumentType(documentType);
            if(needHandelType && needDocumentType ){
                abstractCrawlerPageProcessor.handelPage(page);
            }
        }
    }

}
