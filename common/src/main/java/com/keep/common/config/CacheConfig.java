package com.keep.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache loadingCache(){

        Cache<String, String> loadingCache = Caffeine.newBuilder()
                .initialCapacity(3) //cache的初始容量
                .maximumSize(5)//cache最大缓存数
                //设置写缓存后n秒钟过期
                .expireAfterWrite(30, TimeUnit.MINUTES)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();
        return loadingCache;
    }
}
