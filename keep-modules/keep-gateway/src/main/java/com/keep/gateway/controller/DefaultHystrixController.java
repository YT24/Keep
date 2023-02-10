package com.keep.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认降级处理
 */
@Slf4j
@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public Map<String,Object> defaultfallback(){
        log.error("降级操作...");
        Map<String,Object> map = new HashMap<>();
        map.put("code",555);
        map.put("msg","服务连接超时异常！！！");
        map.put("data",null);
        return map;
    }
}