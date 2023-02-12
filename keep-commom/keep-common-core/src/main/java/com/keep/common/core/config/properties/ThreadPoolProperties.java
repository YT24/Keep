package com.keep.common.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "threadpool")
@Data
public class ThreadPoolProperties {

    private Integer corePoolSize;

    private Integer keepAliveSeconds;

    private Integer maxPoolSize;

    private Integer queueCapacity;
}