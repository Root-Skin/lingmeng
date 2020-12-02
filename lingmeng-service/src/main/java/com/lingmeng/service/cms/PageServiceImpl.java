package com.lingmeng.service.cms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.api.cms.IpageService;
import com.lingmeng.cms.vo.cmsPageListReq;
import com.lingmeng.dao.cms.CmsPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements IpageService {
    @Autowired
    private CmsPageMapper cmsPageMapper;


    @Override
    public IPage pageList(cmsPageListReq req, Page page) {
        IPage iPage = cmsPageMapper.pageList(req, page);
        return iPage;
    }
}
