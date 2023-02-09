package com.keep.app.controller;

import com.keep.common.domain.entity.ResponseResult;
import com.keep.service.bean.RocketMqMessage;
import com.keep.service.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqController {

    @Autowired
    private ProducerService producerService;


    @PostMapping("/api/v1/send")
    public ResponseResult send(@RequestBody RocketMqMessage rocketMqMessage){
        producerService.send(rocketMqMessage);
        return ResponseResult.success();
    }
}
