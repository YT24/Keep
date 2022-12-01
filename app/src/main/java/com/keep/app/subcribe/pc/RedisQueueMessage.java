package com.keep.app.subcribe.pc;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RedisQueueMessage {

    // 消息
    private String content;

    public RedisQueueMessage() {
    }
    // 定义一个带参数的构造方法方便初始化
    public RedisQueueMessage(String content) {
        this.content = content;
    }
}