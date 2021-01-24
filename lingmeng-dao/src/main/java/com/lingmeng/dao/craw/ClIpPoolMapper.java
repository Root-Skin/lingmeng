package com.lingmeng.dao.craw;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.craw.ClIpPool;

import java.util.List;

public interface ClIpPoolMapper extends BaseMapper<ClIpPool> {
    int deleteByPrimaryKey(String id);

    int insert(ClIpPool record);

    int insertSelective(ClIpPool record);

    ClIpPool selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClIpPool record);

    int updateByPrimaryKey(ClIpPool record);

    /**
     * 查询所有数据
     *
     * @param record
     * @return
     */
    List<ClIpPool> selectList(ClIpPool record);

    /**
     * 查询可用的列表
     *
     * @param record
     * @return
     */
    List<ClIpPool> selectAvailableList(ClIpPool record);
}
