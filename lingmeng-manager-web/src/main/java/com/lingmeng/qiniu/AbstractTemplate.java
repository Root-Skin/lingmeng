package com.lingmeng.qiniu;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author skin
 * @Date  2021/1/28 13:35
 * @description 抽象模版
 **/
@Data
@AllArgsConstructor
public abstract class AbstractTemplate {

    int type;

    /**
     * @author skin
     * @param endUrl	文件类型
     * @param uuid      文件名称
     * @Date  2021/1/28 13:36
     * @description
     **/
    abstract Map<String,Object> getQiniuInfo(String endUrl,String uuid);
}
