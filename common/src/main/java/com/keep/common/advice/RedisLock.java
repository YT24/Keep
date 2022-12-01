package com.keep.common.advice;

import com.keep.common.config.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class RedisLock {


    @Pointcut("@annotation(com.keep.common.annotation.RedisLock)")
    public void lockCut(){}

    @Around("lockCut() && @annotation(redisLock)")
    public Object logAround(ProceedingJoinPoint joinPoint, com.keep.common.annotation.RedisLock lock) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object obj;
        // 获取锁
        String lockName = getLockName(lock);
        RLock rLock = SpringContextHolder.getBean(Redisson.class).getLock(lockName);
        try {
            // 加锁
            rLock.lock();
            // 执行原逻辑
            obj = joinPoint.proceed(args);
        } finally {
            // 释放锁
            rLock.unlock();
        }
        return Objects.isNull(obj) ? null: obj;
    }

    private String getLockName(com.keep.common.annotation.RedisLock lock){
        StringBuilder lockName = new StringBuilder("lock");
        if (Objects.nonNull(lock.name().toString())){
            lockName.append(":").append(lock.name().toString());
        }
        return lockName.toString();
    }
}