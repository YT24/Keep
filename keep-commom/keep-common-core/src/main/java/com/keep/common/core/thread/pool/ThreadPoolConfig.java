package com.keep.common.core.thread.pool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 线程池配置
 *
 * @author
 * @date
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "threadpool")
public class ThreadPoolConfig {
 
    /**
     * 核心线程数
     */
    private Integer corePoolSize;
 
    /**
     * 最大线程数
     */
    private Integer maxPoolSize;
 
    /**
     * 队列容量
     */
    private Integer queueCapacity;
 
    /**
     * 线程活跃时间（秒）
     * （线程存活保持时间）当线程池中线程数大于核心线程数时，线程的空闲时间如果超过线程存活时间，
     *  那么这个线程就会被销毁，直到线程池中的线程数小于等于核心线程数。
     */
    private Integer keepAliveSeconds;

}