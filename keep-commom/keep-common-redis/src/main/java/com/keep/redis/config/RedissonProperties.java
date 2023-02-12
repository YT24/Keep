package com.keep.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonProperties {

    public String host;

    public String port;

    public String password;

    public Integer timeOut;
}