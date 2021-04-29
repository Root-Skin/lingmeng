package com.lingmeng.controller.redirct;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Redirct")
public class Redirct {

    @PostMapping("/RedirctPath")
    public String RedirctPath(@RequestHeader(value = "Referer", required = false) String referer) {
        if (StringUtils.isEmpty(referer)) {
            return "redirect" + referer;
        }
        return "redirect+seckill-index.html";
    }
}
