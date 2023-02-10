package com.keep.app.subcribe.pc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer implements RedisQueueConsumer {


    @Override
    public String getQueueName() {
        return "orderConsumer";
    }

    @Override
    public void getMessage(String redisQueueMessage) {
        log.info("接收到了消息："+redisQueueMessage.toString());
    }

    @Override
    public void error(String error) {

    }
}