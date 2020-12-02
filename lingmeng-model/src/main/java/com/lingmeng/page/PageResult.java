package com.lingmeng.page;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResult<T> {
    /** 结果数据集合 **/
    private List<T> resultList = new ArrayList<T>();

    /** 数据总量 **/
    private long totalCount;

    /**
     * 总页数
     */
    private int pageTotal;

    public PageResult(long totalCount, int pageTotal,List<T> resultList) {
        this.resultList = resultList;
        this.totalCount = totalCount;
        this.pageTotal = pageTotal;
    }
}
