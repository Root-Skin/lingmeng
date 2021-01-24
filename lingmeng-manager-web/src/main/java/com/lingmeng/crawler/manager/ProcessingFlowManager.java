package com.lingmeng.crawler.manager;


import com.lingmeng.crawler.config.CrawlerConfig;
import com.lingmeng.crawler.core.parse.ParseItem;
import com.lingmeng.crawler.enums.CrawlerEnum;
import com.lingmeng.crawler.process.ProcessFlow;
import com.lingmeng.crawler.process.entity.CrawlerComponent;
import com.lingmeng.crawler.process.entity.ProcessFlowData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.Scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;


 /**
  * @Author skin
  * @Date  2021/1/1
  * @Description 流程管理器
  *
  * 前置数据处理
  * 对ProcessFlow 接口类型的类进行前置实例化做一些前置处理
  * 例如AbstractOriginalDataProcess 类的 handel 方式 初始化URL 以及初始化 代理数据
  * 并生成Spider 并自定启动
  * 是爬虫服务的入口
  */
@Component
@Log4j2
public class ProcessingFlowManager {

    @Autowired
    private CrawlerConfig crawlerConfig;

     /**
      * @Author skin
      * @Date  2021/1/1
      * @Description 这里的作用是 注入实现了ProcessFlow这个接口的所有类
      **/
    @Resource
    private List<ProcessFlow> processFlowList;


    /**
     * spring启动的时候初始化方法(依赖注入后自动调用)
     * 通过子类的优先级进行排序
     * 初始化spider
     * spring 启动的时候就会进行调用
     * 对实现ProcessFlow接口的类根据getPriority() 接口对实现类进行从小到大的排序
     * 实现有序的责任链模式 一个模块处理一件事然后将数据传递到下个模块交给下各模块进行处理
     */
    @PostConstruct
    public void initProcessingFlow(){
        if(!CollectionUtils.isEmpty(processFlowList)){
            processFlowList.sort(new Comparator<ProcessFlow>() {
                @Override
                public int compare(ProcessFlow o1, ProcessFlow o2) {
                    if(o1.getPriority() > o2.getPriority()){
                        return 1;
                    }else if(o1.getPriority() < o2.getPriority()){
                        return -1;
                    }
                    return 0;
                }
            });
        }
        Spider spider = configSpider();
        crawlerConfig.setSpider(spider);
    }

    private Spider configSpider() {
        Spider spider = initSpider();
        spider.thread(5);
        return spider;
    }

     /**
      * 根据ProcessFlow接口的getComponentType接口类型生成spider对象
      * @return
      *
      */
        private Spider initSpider() {
        Spider spider = null;
        CrawlerComponent component = getComponent(processFlowList);
        if(null != component){
            PageProcessor pageProcessor = component.getPageProcessor();
            if(pageProcessor!=null){
                spider=Spider.create(pageProcessor);
            }
            if(null!=spider && null != component.getScheduler()){
                spider.setScheduler(component.getScheduler());
            }
            if(null != spider && null != component.getDownloader()){
                spider.setDownloader(component.getDownloader());
            }
            List<Pipeline> pipelineList = component.getPipelineList();
            if(null != spider && !CollectionUtils.isEmpty(pipelineList)){
                for (Pipeline pipeline : pipelineList) {
                    spider.addPipeline(pipeline);
                }
            }
        }
        return spider;
    }

    /**
     * 抓取组件的封装
     * 根据接口 CrawlerEnum.ComponentType getComponentType() 封装组件CrawlerComponent
     * @param processFlowList
     * @return
     */
    private CrawlerComponent getComponent(List<ProcessFlow> processFlowList) {
        CrawlerComponent component = new CrawlerComponent();
        for (ProcessFlow processFlow : processFlowList) {
            if(processFlow.getComponentType() == CrawlerEnum.ComponentType.PAGEPROCESSOR){
                //fixme 接口强制转换
                component.setPageProcessor((PageProcessor) processFlow);
            }else if(processFlow.getComponentType() == CrawlerEnum.ComponentType.PIPELINE){
                //fixme 接口强制转换
                component.addPipeline((Pipeline) processFlow);
            }else if(processFlow.getComponentType() == CrawlerEnum.ComponentType.DOWNLOAD){
                component.setDownloader((Downloader) processFlow);
            }else if(processFlow.getComponentType() == CrawlerEnum.ComponentType.SCHEDULER){
                component.setScheduler((Scheduler) processFlow);
            }
        }
        return component;
    }



    /**
     * 开始处理爬虫任务
     * @param parseItemList
     * @param handelType
     */
    public void startTask(List<ParseItem> parseItemList, CrawlerEnum.HandelType handelType){
        ProcessFlowData processFlowData = new ProcessFlowData();
        processFlowData.setHandelType(handelType);
        processFlowData.setParseItemList(parseItemList);
        for (ProcessFlow processFlow : processFlowList) {
            processFlow.handel(processFlowData);
        }
        crawlerConfig.getSpider().start();
    }

    /**
     * 正向爬取
     */
    public void handel(){
        startTask(null,CrawlerEnum.HandelType.FORWARD);
    }

     /**
      * 逆向处理
      */
//     public void reverseHandel() {
//         List<ParseItem> parseItemList = crawlerNewsAdditionalService.queryIncrementParseItem(new Date());
//         handelReverseData(parseItemList);
//         log.info("开始进行数据反向更新，增量数据数量：{}", parseItemList.size());
//         if (null != parseItemList && !parseItemList.isEmpty()) {
//             startTask(parseItemList, CrawlerEnum.HandelType.REVERSE);
//         } else {
//             log.info("增量数据为空不进行增量数据更新");
//         }
//     }

     /**
      * 反向同步数据处理
      *
      * @param parseItemList
      */
     public void handelReverseData(List<ParseItem> parseItemList) {
         if (null != parseItemList && !parseItemList.isEmpty()) {
             for (ParseItem item : parseItemList) {
                 item.setDocumentType(CrawlerEnum.DocumentType.PAGE.name());
                 item.setHandelType(CrawlerEnum.HandelType.REVERSE.name());
             }
         }
     }

 }
