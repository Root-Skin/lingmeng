package com.lingmeng.service;

import com.lingmeng.api.MailService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.utils.VerificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Date;


//@Service
public class MailServiceImpl implements MailService {

    private String code;
    /**
     * 发送时间
     */
    private Date sendTime;

    @Autowired
    private JavaMailSenderImpl mailSender;


    @Override
    public RestReturn getCode(String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("验证码");//设置邮件标题
        code = VerificationUtils.generateVerCode();
        sendTime = new Date();
        message.setText("尊敬的用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + code + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");    //设置邮件正文
        message.setFrom("huangtaosta@163.com");//发件人
        message.setTo(receiver);//收件人
        mailSender.send(message);//发送邮件
        return RestReturn.ok("发送成功");
    }
}
