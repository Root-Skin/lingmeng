package com.lingmeng.common.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class NormalOrderMqConf {


    public static final String NORMAL_LINGMENG_EXCHANGE = "normal_lingmeng_order_exchange";
    public static final String NORMAL_LINGMENG_QUEUE = "normal_lingmeng_order_queue";

    public static final String NORMAL_LINGMENG_DEAD_EXCHANGE = "normal_lingmeng_order_dead_exchange";


    public static final String NORMAL_LINGMENG_DEAD_ROUTE_KEY = "normal_lingmeng_dead_route_key";
    public static final String NORMAL_LINGMENG_DEAD_QUEUE = "normal_lingmeng_order_dead_queue";

    // 定义交换机
    @Bean
    public FanoutExchange normalOrder() {
        return new FanoutExchange(NORMAL_LINGMENG_EXCHANGE, true, false);
    }
    //死信交换机
    @Bean
    public DirectExchange normalOrderDeadLetterExchange() {
        return new DirectExchange(NORMAL_LINGMENG_DEAD_EXCHANGE, true, false);
    }

    //订单业务队列(配置对应的死信交换机和死信队列)
    @Bean
    public Queue normalOrderQueue() {
        Map<String,Object> args = new HashMap<>(3);
        args.put("x-message-ttl", 5000);
        args.put("x-dead-letter-exchange",NORMAL_LINGMENG_DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key",NORMAL_LINGMENG_DEAD_ROUTE_KEY);
        return QueueBuilder.durable(NORMAL_LINGMENG_QUEUE).withArguments(args).build();
    }

    @Bean
    public Queue normalOrderDeadLetterQueue() {
        return new Queue(NORMAL_LINGMENG_DEAD_QUEUE);
    }

    //将业务队列和业务交换机进行绑定
    @Bean
    public Binding normalOrderBinding(@Qualifier("normalOrderQueue") Queue queue,
                                          @Qualifier("normalOrder") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    //将死信队列和死信交换机绑定
    @Bean
    public Binding  normalOrderDeadLetterBinding(@Qualifier("normalOrderDeadLetterQueue") Queue queue,
                                          @Qualifier("normalOrderDeadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(NORMAL_LINGMENG_DEAD_ROUTE_KEY);
    }

}
