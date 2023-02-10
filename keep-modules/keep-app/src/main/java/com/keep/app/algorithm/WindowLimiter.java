package com.keep.app.algorithm;//package com.example.bserver.limit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 新建一个本地缓存，每5s为一个时间窗口，每1s为一个时间片段，时间片段作为缓存的key，
 * 原子类计数器作为缓存的value。每秒发送随机数量的请求，计算每个时间片段的前5秒内的累加请求数量，超出阈值则限流。
 *
 * 滑动时间窗口计数器算法思想：针对固定时间算法会在临界点存在瞬间大流量冲击的场景，滑动时间窗口计数器算法应运而生。
 * 它将时间窗口划分为更小的时间片段，每过一个时间片段，我们的时间窗口就会往右滑动一格，每个时间片段都有独立的计数器。
 * 我们在计算整个时间窗口内的请求总数时会累加所有的时间片段内的计数器。时间窗口划分的越细，那么滑动窗口的滚动就越平滑，限流的统计就会越精确。
 *
 */
@Slf4j
public class WindowLimiter {

    private LoadingCache<Long, AtomicLong> counter =
            CacheBuilder.newBuilder()
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build(new CacheLoader<Long, AtomicLong>() {
                        @Override
                        public AtomicLong load(Long seconds) throws Exception {
                            return new AtomicLong(0);
                        }
                    });
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
    //限流阈值
    private long limit = 15;

    /**
     * 滑动时间窗口
     * 每隔1s累加前5s内每1s的请求数量，判断是否超出限流阈值
     */
    public void slideWindow() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                long time = System.currentTimeMillis() / 1000;
                //每秒发送随机数量的请求
                int reqs = (int) (Math.random() * 5) + 1;
                counter.get(time).addAndGet(reqs);
                long nums = 0;
                // time windows 5 s
                for (int i = 0; i < 5; i++) {
                    nums += counter.get(time - i).get();
                }
                log.info("time=" + time + ",nums=" + nums);
                if (nums > limit) {
                    log.info("限流了,nums=" + nums);
                }
            } catch (Exception e) {
                log.error("slideWindow error", e);
            } finally {
            }
        }, 5000, 1000, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        new WindowLimiter().slideWindow();
    }
}