package com.lingmeng.dao.cms;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.cms.model.Cms;
import com.lingmeng.cms.vo.cmsPageListReq;
import org.apache.ibatis.annotations.Param;



public interface CmsPageMapper extends BaseMapper<Cms> {

   IPage<Cms> pageList(@Param("req") cmsPageListReq req, @Param("page") Page page);
}
