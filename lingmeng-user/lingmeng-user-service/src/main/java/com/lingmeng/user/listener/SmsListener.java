package com.lingmeng.user.listener;


import com.lingmeng.user.client.SmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private  static  final Logger logger = LoggerFactory.getLogger(SmsListener.class);

     @Autowired
     private SmsClient smsClient;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "lingmeng.email.queue",durable ="true"),
            exchange = @Exchange(
                    value = "lingmeng.email.exchange",
                    ignoreDeclarationExceptions = "true"
            ),key = {"email.verify.code"}))
    public void listenEmail(String msg){
        //没有监听到消息
        if(msg ==null ){
            return;
        }
        logger.info("开始消费消息");

        //发送验证码
        smsClient.getCode(msg);
    }
}
