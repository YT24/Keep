package com.keep.kafka.controller;

import com.keep.kafka.er.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class KafkaController {

    public static final String topic = "TEST";

    @Autowired
    KafkaProducer kafkaProducer;

    @PutMapping("/api/kafka/send/msg")
    public void senfKafkaMsg(){
        Map<String,Object> msg = new HashMap<>();
        msg.put("k","v");
        kafkaProducer.send(topic,msg.toString());
    }
}
