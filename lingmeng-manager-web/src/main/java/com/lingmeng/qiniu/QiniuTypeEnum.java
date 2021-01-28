package com.lingmeng.qiniu;

/**
 * @author skin
 * @param null
 * @Date  2021/1/28 14:18
 * @description 七牛类型枚举, BaseUrl的值3D是不需要的
 **/
public interface QiniuTypeEnum {

    //医院模型
    /**
     * 3D
     */
    int THREE_DIMENSIONAL = 1;

    /**
     * 图片
     */
    int PHOTO = 3;
    String PHOTO_BASEURL = "/neuron-qiniu/photos/";


    //neuron 模型
    /**
     * 睿美云3D(客户端)
     */
    int NEURON_THREE_DIMENSIONAL = 5;




}
