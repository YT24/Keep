package com.keep.redis.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CacheConfig {

    private static final int INITIAL_CACHE_SIZE = 50;

    private static final long MAX_CACHE_SIZE = 100_000;

    private static final long EXPIRE_AFTER_WRITE = 60 * 60;

    @Bean
    public Cache loadingCache(){

        Cache<String, String> loadingCache = Caffeine.newBuilder()
                .initialCapacity(INITIAL_CACHE_SIZE) //cache的初始容量
                .maximumSize(MAX_CACHE_SIZE)//cache最大缓存数
                //设置写缓存后n秒钟过期
                .expireAfterWrite(EXPIRE_AFTER_WRITE, TimeUnit.SECONDS)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();
        return loadingCache;
    }
}
