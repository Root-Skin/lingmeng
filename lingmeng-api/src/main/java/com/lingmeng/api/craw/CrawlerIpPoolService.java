package com.lingmeng.api.craw;

import com.lingmeng.craw.ClIpPool;
import com.lingmeng.crawler.core.proxy.CrawlerProxy;

import java.util.List;

public interface CrawlerIpPoolService {
    /**
     * 保存方法
     *
     * @param clIpPool
     */
     void saveCrawlerIpPool(ClIpPool clIpPool);

    /**
     * 检查代理Ip 是否存在
     *
     * @param host
     * @param port
     * @return
     */
     boolean checkExist(String host, int port);

    /**
     * 更新方法
     *
     * @param clIpPool
     */
     void updateCrawlerIpPool(ClIpPool clIpPool);

    /**
     * 查询所有数据
     *
     * @param clIpPool
     */
     List<ClIpPool> queryList(ClIpPool clIpPool);

    /**
     * 获取可用的列表
     *
     * @return
     */
     List<ClIpPool> queryAvailableList(ClIpPool clIpPool);


     void delete(ClIpPool clIpPool);


    void unvailableProxy(CrawlerProxy proxy, String errorMsg);
}
