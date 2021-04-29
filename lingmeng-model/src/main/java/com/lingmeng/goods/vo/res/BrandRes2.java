package com.lingmeng.goods.vo.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BrandRes2 {
    /** 品牌名字 */
    private String id ;
    /** 品牌名字 */
    private String brandName ;
    /** 品牌图片 */
    private String brandPic ;
    /** 品牌首字母 */
    private String brandLetter ;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createdTime ;

}
