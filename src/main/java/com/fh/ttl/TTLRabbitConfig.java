package com.fh.ttl;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * TTL-配置
 */
@Configuration
public class TTLRabbitConfig {

    /**
     * 普通队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<>(16);
        args.put("x-dead-letter-exchange", "ttl-ex"); // 转发路由
        args.put("x-dead-letter-routing-key", "ttl-queue");// 转发队列
        args.put("x-message-ttl", 60000L);// 超时时间
        return new Queue("queue", true, false, true, args);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue ttlQueue() {
        return new Queue("ttl-queue", true);
    }

    /**
     * 普通路由
     *
     * @return
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("ex");
    }

    /**
     * 死信路由
     *
     * @return
     */
    @Bean
    public DirectExchange ttlExchange() {
        return new DirectExchange("ttl-ex");
    }

    /**
     * 普通绑定
     *
     * @return
     */
    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(queue()).to(exchange()).with("queue");
    }

    /**
     * 死信绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTTLQueue() {
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("ttl-queue");
    }
}
