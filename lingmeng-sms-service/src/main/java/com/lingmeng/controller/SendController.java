package com.lingmeng.controller;

import com.lingmeng.api.MailService;
import com.lingmeng.base.RestReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    @Autowired
    private MailService mailService;

    @GetMapping("/sms/getCode")
    public RestReturn verCode(@RequestParam String receiver) {
        return mailService.getCode(receiver);
    }
}
