package com.lingmeng.crawler.core.cookie;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class CrawlerCookie {


    /**
     * cookie名称
     */
    private String name;
    /**
     * cookie 值
     */
    private String value;
    /**
     * 域名
     */
    private String domain;
    /**
     * 路径
     */
    private String path;


    /**
     * 过期时间(精确到天 没有时分秒)
     */
    private Date expire;

    /**
     * 是否是必须的
     */
    private boolean isRequired;


}
