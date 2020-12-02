package com.lingmeng.common.listen;


import com.lingmeng.api.good.es.ISearchService;
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
  * @Description 增加 修改 ,删除商品监听接口
  **/
@Component
public class GoodsListener {


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
    public void listenCreate(String id) throws Exception {
        if (id == null) {
            return;
        }
        // 创建或更新索引
        this.searchService.createIndex(id);
    }
    //开发人员1的代码
     //再次新增
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
        //开发环境代码
        //开发人员2的开发

    }

}
