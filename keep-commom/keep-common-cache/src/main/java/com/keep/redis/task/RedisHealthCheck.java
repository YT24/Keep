package com.keep.redis.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class RedisHealthCheck {

    private Boolean UP = Boolean.FALSE;

    public static final String PONG = "PONG";

    @Autowired
    private RedisTemplate redisTemplate;


    @Scheduled(cron = "0/5 * * * * ?")
    public boolean checkRedisHeath() {
        try {
            Object o = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.ping();
                }
            });
            if ((Objects.nonNull(o) && Objects.equals(o.toString(), PONG))) {
                this.UP = Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("redis 连接异常：{}", e);
        } finally {
            return this.UP;
        }

    }
}
