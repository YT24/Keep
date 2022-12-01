package com.keep.app.subcribe.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyRedisThreadPool {
    // 线程池创建类
    private ThreadPoolExecutor mexecutor;

    //
    private static MyRedisThreadPool instance;

    public MyRedisThreadPool(int coreNum) {
        if (mexecutor == null) {
            // 用单例模式创建线程池，保留2个核心线程，最多线程为CPU个数的2n+1的两倍.
            mexecutor = new ThreadPoolExecutor(coreNum == 0 ? 3 : coreNum, coreNum, 0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<Runnable>(),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
        }
    }

    public static MyRedisThreadPool getInstance(int coreNum) {
        if (instance == null) {
            instance = new MyRedisThreadPool(coreNum);
            log.info("MyRedisThreadPool线程池已经开启");
        }
        return instance;
    }

    public void executor(Runnable runnable) {
        if (null == runnable) {
            return;
        }
        mexecutor.execute(runnable);
    }


    public void destroy() {
        if (mexecutor != null) {
            // 终止线程池
            mexecutor.shutdown();
        }
    }


    public void getInfo() {
        if (null == mexecutor) {
            return;
        }
        int queueSize = mexecutor.getQueue().size();
        log.info("当前排队线程数：" + queueSize);

        int activeCount = mexecutor.getActiveCount();
        log.info("当前活动线程数：" + activeCount);

        long completedTaskCount = mexecutor.getCompletedTaskCount();
        log.info("执行完成线程数：" + completedTaskCount);

        long taskCount = mexecutor.getTaskCount();
        log.info("总线程数：" + taskCount);
    }
}