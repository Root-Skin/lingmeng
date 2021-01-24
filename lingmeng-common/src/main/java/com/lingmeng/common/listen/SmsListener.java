package com.lingmeng.common.listen;


import com.lingmeng.api.sms.MailService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
  * @Author skin
  * @Date  2020/9/4
  * @Description  发送邮件的监听器
  **/
@Component
public class SmsListener {


    @Autowired
    private MailService mailService;

    private  static  final Logger logger = LoggerFactory.getLogger(SmsListener.class);


    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "lingmeng.email.queue",durable ="true"),
            exchange = @Exchange(
                    value = "lingmeng.email.exchange",
                    ignoreDeclarationExceptions = "true"
            ),key = {"email.verify.code"}))
    public void listenEmail(String msg){

        if(msg ==null ){
            return;
        }
        logger.info("开始消费消息");
        //发送启动日志
        mailService.getCode(msg);

    }

    /**
     * @author skin
     * @param msg
     * @Date  2021/1/18 17:55
     * @description
     **/
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "lingmeng.startLog.queue",durable ="true"),
            exchange = @Exchange(
                    value = "lingmeng.startLog.exchange",
                    ignoreDeclarationExceptions = "true"
            ),key = {"startLog"}))
    public void listenStartLog(final Message msg, final Channel channel){
        final String message = new String(msg.getBody());
        //没有监听到消息
        if(msg ==null ){
            return;
        }

        logger.info("开始消费消息");
        //发送验证码
        try {
            mailService.sendStartLog(message);
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
