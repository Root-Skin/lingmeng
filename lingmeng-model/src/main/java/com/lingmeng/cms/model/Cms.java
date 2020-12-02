package com.lingmeng.cms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lingmeng.base.SuperEntity;
import lombok.Data;

@TableName("lm_page")
@Data
public class Cms  extends SuperEntity {
    /** 站点ID */
    private String siteId ;
    /** 页面名称 */
    private String pageName ;
    /** 页面访问地址 */
    private String pagePath ;
    /** 页面参数 */
    private String pageParam ;
    /** 页面物理路径 */
    private String pagePsyPath ;
    /** 页面模板ID */
    private String templateId ;
    /** 页面状态;(0.关闭  1,启用) */
    private Boolean pageStatus ;
}
