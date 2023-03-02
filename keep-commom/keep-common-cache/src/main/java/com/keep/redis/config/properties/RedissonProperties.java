package com.keep.redis.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonProperties {

    public String host;

    public String port;

    public String password;

    public Integer timeOut;
}