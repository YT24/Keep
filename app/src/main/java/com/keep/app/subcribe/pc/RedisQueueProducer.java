package com.keep.app.subcribe.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisQueueProducer {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 发送消息
     *
     * @param queueName    队列名称
     * @param redisQueueMessage 消息
     */
    public void sendMessage(String queueName, RedisQueueMessage redisQueueMessage) {

        redisTemplate.opsForList().leftPush(queueName,redisQueueMessage);
    }
}