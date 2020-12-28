package com.lingmeng.common.listen;


import com.lingmeng.api.good.es.ISearchService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
  * @Author skin
  * @Date  2020/11/11
  * @Description 增加 修改 ,删除商品监听接口
  **/
@Component
@Slf4j
public class GoodsListener {

     static final Logger logger = LoggerFactory.getLogger(GoodsListener.class);
    @Autowired
    private ISearchService searchService;

    /**
     * 处理insert和update的消息
     *
     * @param id
     * @throws Exception
     */
    // ignoreDeclarationExceptions 记录异常,并继续声明其它元素
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "lingmeng.create.index.queue", durable = "true"),
            exchange = @Exchange(
                    value = "lingmeng.good.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"good.insert", "good.update"}))

    public void listenCreate(final Message msg, final Channel channel) throws Exception {
        final String id = new String(msg.getBody());

        if (id == null) {
            return;
        }
        // 创建或更新索引
        //特点：只有服务端收到确认信号，
        log.info("开始创建新的索引");
        logger.info("开始创建新的索引");
        //  try catch 就表示被消费掉了
        try {
            this.searchService.createIndex(id);
        } catch (Exception e) {
            e.printStackTrace();
            //todo 失败后要有重试机制，可记录到系统数据库，采用定期扫描重发的方式。
        }
        System.out.println("这里如果有异常还会不会执行");
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);

    }
    //开发中的代码

    /**
     * 处理delete的消息
     *
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "lingmeng.delete.index.queue", durable = "true"),
            exchange = @Exchange(
                    value = "lingmeng.good.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = "good.delete"))
    public void listenDelete(String id) {
        if (id == null) {
            return;
        }
        // 删除索引
        this.searchService.deleteIndex(id);

        //测试分支处理BUg添加的代码

        //uat演示环境出现BUG
    }

}
