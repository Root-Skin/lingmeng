package com.lingmeng.service.sms;

import com.lingmeng.base.RestReturn;
import com.lingmeng.api.sms.MailService;
import com.lingmeng.common.utils.cache.CacheKey;
import com.lingmeng.common.utils.sms.VerificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public RestReturn getCode(String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("验证码");//设置邮件标题
        String code = VerificationUtils.generateVerCode();
        //放入缓存5分钟
        this.redisTemplate.opsForValue().set(CacheKey.EMAIL_CODE+receiver,code,5, TimeUnit.MINUTES);
        message.setText("尊敬的用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + code + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");    //设置邮件正文
        message.setFrom("huangtaosta@163.com");//发件人
        message.setTo(receiver);//收件人
        mailSender.send(message);//发送邮件
        return RestReturn.ok("发送成功");
    }

    @Override
    public RestReturn sendStartLog(String Text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("系统启动日志");//设置邮件标题

        message.setText(Text);    //设置邮件正文
        message.setFrom("huangtaosta@163.com");//发件人
        message.setTo("huangtaosta@163.com");//收件人
        mailSender.send(message);//发送邮件
        return RestReturn.ok("发送成功");
    }
}
