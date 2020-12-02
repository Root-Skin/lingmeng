package com.lingmeng.controller.good.portal;


import com.lingmeng.api.good.IspuService;
import com.lingmeng.service.goods.GoodSHTMLServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/item")
public class GoodDetailController {

    @Autowired
    private IspuService spuService;

    @Autowired
    private GoodSHTMLServiceImpl goodSHTMLService;

    @GetMapping("/{id}.html")
    public String toItemPage(Model model, @PathVariable("id")String id){
        System.out.println("test");
        Map goodDetail = this.spuService.getGoodDetail(id);
        //页面静态化(不能对主线程产生影响  这里使用多线程)
        this.goodSHTMLService.asyncExcute(id);

        model.addAllAttributes(goodDetail);
        return "item";
    }
}
