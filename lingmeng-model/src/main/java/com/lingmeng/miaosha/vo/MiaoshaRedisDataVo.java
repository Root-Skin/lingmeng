package com.lingmeng.miaosha.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MiaoshaRedisDataVo implements Serializable {

     /**
      * @Author skin
      * @Date  2020/12/11
      * @Description 这里的ID是指的是sku的ID
      **/
    private String id;
    private String title;
    private String images;
    private BigDecimal price;
    private String activeStartTime;
    private String activeEndTime;
    private Integer stock;
    private  Integer spikeTotal;



}
