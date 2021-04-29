package com.lingmeng.dao.craw;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.craw.AdChannelLabel;

public interface AdChannelLabelMapper  extends BaseMapper<AdChannelLabelMapper> {
    int deleteByPrimaryKey(Integer id);

    int insert(AdChannelLabel record);

    int insertSelective(AdChannelLabel record);

    AdChannelLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdChannelLabel record);

    int updateByPrimaryKey(AdChannelLabel record);

    /**
     * 根据labelId查询
     * @param id
     * @return
     */
    AdChannelLabel selectByLabelId(String id);

}
