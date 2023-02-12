package com.keep.service.producer;

import com.keep.service.bean.RocketMqMessage;
import com.keep.service.enums.MqDelayLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ProducerService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.topic}")
    private String topic;


    public void send(RocketMqMessage msg){
        //rocketMQTemplate.convertAndSend(topic,msg);
        Message message = MessageBuilder.withPayload(msg).setHeader("KEYS", UUID.randomUUID().toString()).build();

        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("--------发送成功",sendResult.getMsgId());
                log.info("--------sendResult[ msgId: {} ]",sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("-------发送异常:{}",throwable.getStackTrace());
            }
        });

        // 延时发送
        rocketMQTemplate.syncSend(topic,message,3000, MqDelayLevelEnum.FOUR.getLevel());

    }
}
