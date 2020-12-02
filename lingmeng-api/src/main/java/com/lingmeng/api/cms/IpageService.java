package com.lingmeng.api.cms;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.cms.vo.cmsPageListReq;


public interface IpageService {

     IPage pageList(cmsPageListReq req, Page page);
}
