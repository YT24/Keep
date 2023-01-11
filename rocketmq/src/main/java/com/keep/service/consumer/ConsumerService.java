package com.keep.service.consumer;

import com.keep.service.bean.RocketMqMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener( topic = "${rocketmq.topic}", consumerGroup = "${rocketmq.producer.group}", selectorType = SelectorType.TAG)
public class ConsumerService implements RocketMQListener<RocketMqMessage> {

    /**
     * 若 发生异常 且不能捕获 则不提交偏移量
     * @param msg
     */
    @SneakyThrows
    @Override
    public void onMessage(RocketMqMessage msg) {
        log.info("--------消费 msg：{}",msg);
        //throw new RuntimeException("消费异常");
    }
}
