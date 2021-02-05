package com.lingmeng.crawler.config;


import com.lingmeng.crawler.core.callback.DataValidateCallBack;
import com.lingmeng.crawler.core.parse.ParseRule;
import com.lingmeng.crawler.core.proxy.CrawlerProxyProvider;
import com.lingmeng.crawler.enums.CrawlerEnum;
import com.lingmeng.crawler.helper.CookieHelper;
import com.lingmeng.crawler.helper.CrawlerHelper;
import com.lingmeng.crawler.process.entity.CrawlerConfigProperty;
import com.lingmeng.crawler.utils.SeleniumClient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "crawler.init.url")
public class CrawlerConfig {

//    @Autowired
//    private CruxConfig cruxConfig;
//    @Autowired
//    private proxyConfig proxyConfig;
//    @Autowired
//    private SizeConfig sizeConfig;

    private String prefix;
    private String suffix;
    private Spider spider;

    @Value("${crux.cookie.name}")
    private  String CRUX_COOKIE_NAME;

    @Value("${proxy.isUsedProxyIp}")
    private  Boolean isUsedProxyIp;
    @Value("${crawler.help.nextPagingSize}")
    private  Integer crawlerHelpNextPagingSize;



    @Bean
    public SeleniumClient getSeleniumClient() {
        return new SeleniumClient();
    }
    @Bean
    public CookieHelper getCookieHelper() {
        return new CookieHelper(CRUX_COOKIE_NAME);
    }

    @Bean
    public CrawlerHelper getCrawerHelper() {
        CookieHelper cookieHelper = getCookieHelper();
        CrawlerHelper crawerHelper = new CrawlerHelper();
        DataValidateCallBack dataValidateCallBack = getDataValidateCallBack(cookieHelper);
        crawerHelper.setDataValidateCallBack(dataValidateCallBack);
        return crawerHelper;
    }
    /**
     * 是否使用代理Ip
     */
    @Bean
    public CrawlerProxyProvider getCrawlerProxyProvider() {
        CrawlerProxyProvider crawlerProxyProvider = new CrawlerProxyProvider();
        crawlerProxyProvider.setUsedProxyIp(isUsedProxyIp);
        return crawlerProxyProvider;
    }


    /**
     * 帮助页面抓取Xpath
     */
    private String helpCrawlerXpath = "//div[@class='article-list']/div[@class='article-item-box']/h4/a";
    /**
     * 初始化抓取的Xpath
     */
    private String initCrawlerXpath = "//ul[@class='feedlist_mod']/li[@class='clearfix']/div[@class='list_con']/dl[@class='list_userbar']/dd[@class='name']/a";

    @Bean
    public CrawlerConfigProperty getCrawlerConfigProperty() {
        CrawlerConfigProperty crawlerConfigProperty = new CrawlerConfigProperty();
        crawlerConfigProperty.setInitCrawlerUrlList(getInitCrawlerUrlList());
        crawlerConfigProperty.setHelpCrawlerXpath(helpCrawlerXpath);
        crawlerConfigProperty.setTargetParseRuleList(getTargetParseRuleList());
        crawlerConfigProperty.setCrawlerHelpNextPagingSize(crawlerHelpNextPagingSize);
        crawlerConfigProperty.setInitCrawlerXpath(initCrawlerXpath);
        return crawlerConfigProperty;
    }



    /**
     * 目标页面抓取规则
     *
     * @return
     */
    public List<ParseRule> getTargetParseRuleList() {
        List<ParseRule> parseRuleList = new ArrayList<ParseRule>() {{
            //标题
            add(new ParseRule("title", CrawlerEnum.ParseRuleType.XPATH, "//h1[@class='title-article']/text()"));
            //作者
            add(new ParseRule("author", CrawlerEnum.ParseRuleType.XPATH, "//a[@class='follow-nickName']/text()"));
            //发布日期
            add(new ParseRule("releaseDate", CrawlerEnum.ParseRuleType.XPATH, "//span[@class='time']/text()"));
            //标签
            add(new ParseRule("labels", CrawlerEnum.ParseRuleType.XPATH, "//span[@class='tags-box']/a/text()"));
            //个人空间
            add(new ParseRule("personalSpace", CrawlerEnum.ParseRuleType.XPATH, "//a[@class='follow-nickName']/@href"));
            //阅读量
            add(new ParseRule("readCount", CrawlerEnum.ParseRuleType.XPATH, "//span[@class='read-count']/text()"));
            //点赞量
            add(new ParseRule("likes", CrawlerEnum.ParseRuleType.XPATH, "//div[@class='tool-box']/ul[@class='meau-list']/li[@class='btn-like-box']/button/p/text()"));
            //回复次数
            add(new ParseRule("commentCount", CrawlerEnum.ParseRuleType.XPATH, "//div[@class='tool-box']/ul[@class='meau-list']/li[@class='to-commentBox']/button/p/text()"));
            //html内容
            add(new ParseRule("content", CrawlerEnum.ParseRuleType.XPATH, "//div[@id='content_views']/html()"));

        }};
        return parseRuleList;
    }







    /**
     * 数据校验匿名内部类
     * @param cookieHelper
     * @return
     */
    private DataValidateCallBack getDataValidateCallBack(CookieHelper cookieHelper) {
        return new DataValidateCallBack() {
            @Override
            public boolean validate(String content) {
                boolean flag = true;
                if (StringUtils.isEmpty(content)) {
                    flag = false;
                } else {
                    boolean isContains_acw_sc_v2 = content.contains("acw_sc__v2");
                    boolean isContains_location_reload = content.contains("document.location.reload()");
                    if (isContains_acw_sc_v2 && isContains_location_reload) {
                        flag = false;
                    }
                }
                return flag;
            }
        };
    }


     /**
      * @Author skin
      * @Date  2020/12/31
      * @Description 拼接初始化的URL
      **/
    public List<String> getInitCrawlerUrlList(){
        List<String> initCrawlerUrlList = new ArrayList<>();
        if(StringUtils.isNotEmpty(suffix)){
            String[] urlArray = suffix.split(",");
            if(urlArray!=null && urlArray.length>0){
                for(int i = 0;i<urlArray.length;i++){
                    String initUrl = urlArray[i];
                    if(StringUtils.isNotEmpty(initUrl)){
                        if(!initUrl.toLowerCase().startsWith("http")){
                            initCrawlerUrlList.add(prefix+initUrl);
                        }
                    }
                }
            }
        }
        return initCrawlerUrlList;
    }




}
