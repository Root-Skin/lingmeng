package com.lingmeng.controller.cms;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingmeng.api.cms.IpageService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.cms.model.Cms;
import com.lingmeng.cms.vo.CmsReq;
import com.lingmeng.cms.vo.cmsPageListReq;
import com.lingmeng.dao.cms.CmsPageMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cmsPage")
public class cmsPageController {

    @Autowired
    private IpageService pageService;
    @Autowired
    private CmsPageMapper cmsPageMapper;

    /**
     * @Author skin
     * @Date 2020/11/16
     * @Description 按照分页查询相关页面信息
     **/
    @PostMapping("/pageList")
    public RestReturn pageList(@RequestBody cmsPageListReq req) {
        Page page = new Page<>();
        page.setCurrent(req.getPageNo());
        page.setSize(req.getPageSize());
        IPage iPage = pageService.pageList(req, page);
        return RestReturn.ok(iPage);
    }

    /**
     * @Author skin
     * @Date 2020/11/20
     * @Description 新增cms页面
     **/
    @PostMapping("/addCms")
    public RestReturn addCms(@RequestBody CmsReq req) {
        Cms cms = new Cms();
        BeanUtils.copyProperties(req, cms);
        cms.setPageStatus(false);
        cmsPageMapper.insert(cms);
        return RestReturn.ok("新增成功");
    }

    /**
     * @Author skin
     * @Date 2020/11/20
     * @Description 编辑cms页面(注意为空的情况)
     **/
    @PostMapping("/updateCms")
    public RestReturn updateCms(@RequestBody CmsReq req) {
        Cms cms = cmsPageMapper.selectById(req.getId());
        BeanUtils.copyProperties(req, cms);
        cmsPageMapper.updateById(cms);
        return RestReturn.ok("修改成功");
    }

    /**
     * @Author skin
     * @Date 2020/11/20
     * @Description 查询得到详细信息
     **/
    @GetMapping("/getCmsById")
    public RestReturn getCmsById(@RequestParam String id) {
        Cms cms = cmsPageMapper.selectById(id);
        return RestReturn.ok(cms);
    }

    @GetMapping("/del")
    public RestReturn del(@RequestParam String id) {
        cmsPageMapper.deleteById(id);
        return RestReturn.ok("删除成功");
    }
}
