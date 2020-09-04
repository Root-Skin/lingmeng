package com.lingmeng.user.client;


import com.lingmeng.api.MailService;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "lingmeng-sms-service")
public interface SmsClient  extends MailService {
}
