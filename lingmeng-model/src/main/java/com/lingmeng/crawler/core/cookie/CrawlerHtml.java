package com.lingmeng.crawler.core.cookie;


import com.lingmeng.crawler.core.proxy.CrawlerProxy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CrawlerHtml {


    public CrawlerHtml(String url) {
        this.url = url;
    }


    private String url;

    private String html;

    private CrawlerProxy proxy;

    private List<CrawlerCookie> crawlerCookieList = null;

}
