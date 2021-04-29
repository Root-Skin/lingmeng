package com.lingmeng.common.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MqConf {


    public static final String LINGMENG_EXCHANGE = "lingmeng_order_exchange";
    public static final String LINGMENG_QUEUE = "lingmeng_order_queue";
    public static final String LINGMENG_QUEUE_ROUTE_KEY = "lingmeng_order_queue_route_key";

    public static final String LINGMENG_DEAD_EXCHANGE = "lingmeng_order_dead_exchange";
    public static final String LINGMENG_DEAD_QUEUE = "lingmeng_order_dead_queue";
    public static final String LINGMENG_DEAD_ROUTE_KEY = "lingmeng_dead_route_key";

    // 定义交换机
    @Bean
    public FanoutExchange bussinessExchange() {
        return new FanoutExchange(LINGMENG_EXCHANGE, true, false);
    }
    //死信交换机
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(LINGMENG_DEAD_EXCHANGE, true, false);
    }

    //订单业务队列(配置对应的死信交换机和死信队列)
    @Bean
    public Queue bussinessQueue() {
        Map<String,Object> args = new HashMap<>(3);
        args.put("x-message-ttl", 5000);
        args.put("x-dead-letter-exchange",LINGMENG_DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key",LINGMENG_DEAD_ROUTE_KEY);
        return QueueBuilder.durable(LINGMENG_QUEUE).withArguments(args).build();
    }
    //死信队列
    @Bean
    public Queue deadLetterQueue() {
       return new Queue(LINGMENG_DEAD_QUEUE);
    }



    //将业务队列和业务交换机进行绑定
    @Bean public Binding businessBinding(@Qualifier("bussinessQueue") Queue queue,
                                          @Qualifier("bussinessExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    //将死信队列和死信交换机绑定
    @Bean public Binding  deadLetterBinding(@Qualifier("deadLetterQueue") Queue queue,
                                          @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(LINGMENG_DEAD_ROUTE_KEY);
    }


}
