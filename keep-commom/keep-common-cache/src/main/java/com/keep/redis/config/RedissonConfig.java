package com.keep.redis.config;

import com.keep.redis.config.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson bean管理
 */
@Configuration
public class RedissonConfig {

    @Autowired
    RedissonProperties redissonProperties;

    /**
     * Redisson客户端注册
     * 单机模式
     */

    @Bean(destroyMethod = "shutdown")
    public RedissonClient createRedissonClient(){

        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://"+redissonProperties.host+":"+redissonProperties.port);
        //singleServerConfig.setPassword("12345");
        singleServerConfig.setTimeout(3000);
        return Redisson.create(config);
    }
}