//package com.keep.app.subcribe;
//import com.keep.common.core.config.SpringContextHolder;
//import com.keep.app.subcribe.config.RedisQueueConsumerContainer;
//import com.keep.app.subcribe.pc.RedisQueueConsumer;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import java.time.Duration;
//
///**
// * @Author: buding
// * 消息队列的监听器
// * @DateTime: 2020/2/27 4:48 下午
// */
//@Slf4j
//public class RedisQueueListener implements Runnable {
//
//    private final Long WAITTIME = 30L;
//    private RedisQueueConsumer redisQueueConsumer;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public RedisQueueListener(RedisQueueConsumer redisQueueConsumer) {
//        this.redisQueueConsumer = redisQueueConsumer;
//    }
//
//    @Override
//    public void run() {
//        redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
//        log.info("redis监听器开始监听:{}", redisQueueConsumer.getQueueName());
//        while (RedisQueueConsumerContainer.isRun) {
//            try {
//                Object object = redisTemplate.opsForList().leftPop(redisQueueConsumer.getQueueName(), Duration.ofMillis(WAITTIME));
//                if (object != null) {
//                    log.info("消费message："+object.toString());
//                    //redisQueueConsumer.getMessage(object);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.info("redis监听器错误：{}", redisQueueConsumer.getQueueName());
//            }
//        }
//    }
//}