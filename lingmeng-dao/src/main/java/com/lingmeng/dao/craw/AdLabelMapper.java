package com.lingmeng.dao.craw;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.craw.AdLabel;

import java.util.List;

public interface AdLabelMapper extends BaseMapper<AdLabel> {


    int deleteByPrimaryKey(String id);

    int insert(AdLabel record);

    int insertSelective(AdLabel record);

    AdLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdLabel record);

    int updateByPrimaryKey(AdLabel record);

    List<AdLabel> queryAdLabelByLabels(List<String> labelList);

    List<AdLabel> queryAdLabelByLabelIds(List<String> labelList);
}
