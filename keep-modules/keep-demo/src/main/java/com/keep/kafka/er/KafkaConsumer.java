//package com.keep.kafka.er;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.OffsetAndMetadata;
//import org.apache.kafka.common.TopicPartition;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//
//@Component
//@Slf4j
//public class KafkaConsumer {
//
//    public static final String topic = "TEST";
//
//    @KafkaListener(topics = topic)
//    public void onMessage1(String message, Acknowledgment ack){
//        try {
//            System.out.println(message);
//            log.info("kafka-topic-1接收结果:{}",message);
//            ack.acknowledge();
//        }catch (Exception e){
//            log.error("消费异常：{}",e.getMessage());
//        }
//
//    }
//
//}