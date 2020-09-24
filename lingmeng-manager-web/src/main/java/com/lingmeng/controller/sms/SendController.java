package com.lingmeng.controller.sms;

import com.lingmeng.base.RestReturn;
import com.lingmeng.api.sms.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SendController {
    @Autowired
    private MailService mailService;

    @GetMapping("/sendEmailCode")
    public RestReturn verCode(@RequestParam String receiver) {
        return mailService.getCode(receiver);
    }
}
