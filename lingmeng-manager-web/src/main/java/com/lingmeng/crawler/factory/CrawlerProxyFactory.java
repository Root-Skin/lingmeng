package com.lingmeng.crawler.factory;

import com.lingmeng.crawler.core.proxy.CrawlerProxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import us.codecraft.webmagic.proxy.Proxy;

/**
 * 爬虫代理工厂
 */
public class CrawlerProxyFactory {

    /**
     * 不使用代理
     */
    private static final String NOT_USE_PROXY = "NOT_USE_PROXY";


    /**
     * 代理对象httpclient的代理(静态方法,随类加在而加载只有一份在JVM中)
     * @param crawlerProxy
     * @return
     */
    public static HttpHost getHttpHostProxy(CrawlerProxy crawlerProxy) {
        if (null != crawlerProxy && StringUtils.isNotEmpty(crawlerProxy.getHost()) && null != crawlerProxy.getPort()) {
            return new HttpHost(crawlerProxy.getHost(), crawlerProxy.getPort());
        }
        return null;
    }

    /**
     * 获取webmagic 代理对象
     *
     * @return
     */
    public static Proxy getWebmagicProxy(CrawlerProxy crawlerProxy) {
        if (null != crawlerProxy && StringUtils.isNotEmpty(crawlerProxy.getHost()) && null != crawlerProxy.getPort()) {
            return new Proxy(crawlerProxy.getHost(), crawlerProxy.getPort());
        }
        return null;
    }

    /**
     * 获取selenium 代理对象
     * (将文章交给WebMagic 进行数据抓取，如果抓取过程中出现失败，则采用selenium+Chrome 的方式抓取页面，并进行cookie重置)
     *
     * @return
     */
    public static org.openqa.selenium.Proxy getSeleniumProxy(CrawlerProxy crawlerProxy) {
        if (null != crawlerProxy && StringUtils.isNotEmpty(crawlerProxy.getHost()) && null != crawlerProxy.getPort()) {
            org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
            proxy.setHttpProxy(crawlerProxy.getProxyInfo());
            return proxy;
        }
        return null;
    }

    /**
     * 获取代理信息 (IP 和端口)
     *
     * @param proxy
     * @return
     */
    public static String getCrawlerProxyInfo(CrawlerProxy proxy) {
        String proxyInfo = NOT_USE_PROXY;
        if (null != proxy) {
            proxyInfo = proxy.getProxyInfo();
        }
        return proxyInfo;
    }
}
