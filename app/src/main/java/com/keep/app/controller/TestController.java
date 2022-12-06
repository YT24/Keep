package com.keep.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {


    @Value("${k1}")
    private String k1;

    @GetMapping("get")
    public String get(){
        return k1;
    }
}
