package com.lingmeng.goods.vo.req;

import lombok.Data;

import java.util.List;

@Data
public class BrandReq {
    /** 编辑时候存在ID */
    private String id;
    /** 品牌名字 */
    private String brandName ;
    /** 品牌图片 */
    private String brandPic ;
    /** 品牌首字母 */
    private String brandLetter ;
    /** 品牌的关联项目ID */
    private List<String> categories ;

}
