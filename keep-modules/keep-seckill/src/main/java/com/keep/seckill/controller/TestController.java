package com.keep.seckill.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
public class TestController {


    @SneakyThrows
    public static void main(String[] args) {
        String reg = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        String value = "hdfs://192.168.44.189:8020//test2/";
        boolean matches = Pattern.matches(reg, value);
        System.out.println(matches);
    }
}
