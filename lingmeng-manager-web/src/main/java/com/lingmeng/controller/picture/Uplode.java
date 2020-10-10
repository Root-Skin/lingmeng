package com.lingmeng.controller.picture;

import com.lingmeng.api.pic.picService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pic")
public class Uplode {

    @Autowired
    private picService picService;
    @PostMapping("/upload")
    public RestReturn uploadPc(@RequestParam("file") MultipartFile pic){

        String url = picService.upload(pic);
        if(StringUtils.isEmpty(url)){
            throw  new RestException("图片路径不能为空");
        }
        return RestReturn.ok(url,"上传成功");

    }
}
