package com.lingmeng.model.goods.vo.res;

import lombok.Data;

@Data
public class SpuListRes {

    private String id ;
    /** 标题 */
    private String title ;
    /** 分类名 */
    private String categoryName ;
    /** 品牌名 */
    private String brandName;

    //这里缺少的cid,导致后面回显出现问题

    /** 品牌名 */
    private String cid1;
    /** 品牌名 */
    private String cid2;


}
