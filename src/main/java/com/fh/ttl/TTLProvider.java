package com.fh.ttl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TTL-生产者
 */
@RestController
@RequestMapping("/ttl")
public class TTLProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage")
    public boolean sendMessage() {
        // 发送消息 向普通队列
        rabbitTemplate.convertAndSend("ex", "queue", "i am ttl send time is : " + System.currentTimeMillis());
        return true;
    }

}
