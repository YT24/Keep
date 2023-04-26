package com.keep.seckill;

import com.keep.seckill.service.TGoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    TGoodsService tGoodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        System.out.println(redisTemplate.opsForValue().get("prod:seckill:2"));
    }

}