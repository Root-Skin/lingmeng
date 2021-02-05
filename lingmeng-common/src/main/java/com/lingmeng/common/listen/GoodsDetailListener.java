package com.lingmeng.common.listen;


import com.lingmeng.api.good.es.GoodSHTMLService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 /**
  * @Author skin
  * @Date  2020/11/11
  * @Description 静态页面监听器
  **/
@Component
public class GoodsDetailListener {

     @Autowired
     private GoodSHTMLService goodSHTMLService;

     @RabbitListener(bindings = @QueueBinding(
             value = @Queue(value = "lingmeng.create.web.queue", durable = "true"),
             exchange = @Exchange(
                     value = "lingmeng.good.exchange",
                     ignoreDeclarationExceptions = "true",
                     type = ExchangeTypes.TOPIC),
             key = {"good.insert", "good.update"}))
     public void listenCreate(String id) throws Exception {
         if (id == null) {
             return;
         }
         // 创建页面
         goodSHTMLService.createHtml(id);
     }

     @RabbitListener(bindings = @QueueBinding(
             value = @Queue(value = "lingmeng.delete.web.queue", durable = "true"),
             exchange = @Exchange(
                     value = "lingmeng.good.exchange",
                     ignoreDeclarationExceptions = "true",
                     type = ExchangeTypes.TOPIC),
             key = "good.delete"))
     public void listenDelete(String id) {
         if (id == null) {
             return;
         }
         // 创建页面
         goodSHTMLService.deleteHtml(id);

     }
}
