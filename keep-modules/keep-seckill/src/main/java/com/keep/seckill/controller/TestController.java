package com.keep.seckill.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

@RestController
public class TestController {


    @SneakyThrows
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(1);
        StringBuilder builder = new StringBuilder();
        builder.append(1);
    }
}
