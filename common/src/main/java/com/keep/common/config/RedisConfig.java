package com.keep.common.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@ConditionalOnProperty(prefix = "redis"/*, value = "enable", havingValue = "false"*/)
@ConfigurationProperties(prefix = "redis"/*, value = "enable", havingValue = "false"*/)
@Configuration
public class RedisConfig {

    //@Value("${spring.redis.host}")
    private String host;

    //@Value("${spring.redis.port}")
    private String port;

    //@Value("${spring.redis.password:}")
    private String password;

    //@Value("${spring.redis.timeout}")
    private Integer timeOut;


    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setTimeout(timeOut)
                .setAddress("redis://" + host + ":" + port);

        //change by timmy 兼容某些环境没有密码的窘境。
        if(! StringUtils.isBlank(password) ){
            config.useSingleServer()
                    .setPassword(password);
        }

        return Redisson.create(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);

        return redisTemplate;
    }
}