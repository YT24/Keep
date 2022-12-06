package com.keep.kafka.er;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafka消息发送者
 */
@Slf4j(topic = "keep-KAFKA")
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     *  发送文字消息
     * @param message
     * @return
     */
    public String send(String topic,String message){
        try {
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onSuccess(SendResult<String, Object> result) {
                    System.out.println("msg OK." + result.toString());
                }

                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("msg send failed: " + ex.getMessage());
                }
            });
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return message;
    }
}

