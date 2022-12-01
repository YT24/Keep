package com.keep.app.subcribe.pc;

public interface RedisQueueConsumer {

    // 获取队列名称
    String getQueueName();
    // 获取监听器返回的消息
    void getMessage(String redisQueueMessage);
    // 获取监听器返回的错误消息
    void error(String error);
}