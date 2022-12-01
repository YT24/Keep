package com.keep.common.config;

import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Value("${server.port}")
    private Integer port;

    @Bean
    public ThreadPoolExecutor taskExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,
                16,
                1000,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new NamedThreadFactory("yt-test-"+port+"-exec-"+Thread.currentThread().getId()),
                new ThreadPoolExecutor.AbortPolicy());
        // 四种拒绝策略
        //1，AbortPolicy（默认）直接抛出异常 组织系统正常运行
        //2，CallerRunsPolicy 调用者运行一种调节机制 不会抛弃任务也不会抛出异常 而是将某些任务回退到调用者
        //3，DiscardOldestPolicy 抛弃队列中等待最久的任务，然后把当前任务加入到队列中尝试再次提交当前任务
        //4，DiscardPolicy 直接丢弃任务 不报异常 如果允许任务丢失 最优方案
        return threadPoolExecutor;
    }
}