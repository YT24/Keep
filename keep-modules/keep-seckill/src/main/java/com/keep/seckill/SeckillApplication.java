package com.keep.seckill;

import cn.hutool.core.collection.CollUtil;
import com.keep.seckill.domain.entity.TSeckillGoods;
import com.keep.seckill.service.TSeckillGoodsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.keep"})
@MapperScan(basePackages = "com.keep.**.mapper")
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }


    @SneakyThrows
    @EventListener
    public void deploymentVer(ApplicationReadyEvent event) {


    }



}
