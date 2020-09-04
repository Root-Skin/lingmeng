package com.lingmeng.controller;

import com.lingmeng.api.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    @Autowired
    private MailService mailService;
//    @GetMapping("/verCode")
//    public RestReturn verCode( @RequestParam  String receiver) {
//        return mailService.getCode(receiver);
//    }
}
