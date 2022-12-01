package com.keep.app.subcribe.config;

import com.keep.app.subcribe.pc.OrderConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 添加消费者到消费者容器
 */
@Slf4j
@Configuration
public class RedisPCQueueConfig {

    // 初始化完毕后调取 init
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public RedisQueueConsumerContainer redisQueueConsumerContainer() {
        log.info("redis队列开始加载");
        RedisQueueConsumerContainer redisQueueConsumerContainer = new RedisQueueConsumerContainer();
        // 添加消费者 OrderConsumer 到消费者容器
        redisQueueConsumerContainer.addConsumer(new OrderConsumer());
        log.info("redis队列开始加载成功");
        return redisQueueConsumerContainer;
    }
}