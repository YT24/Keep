package com.keep.redis.advice;

import com.keep.common.core.config.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.Redisson;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class RedissonLock {


    @Pointcut("@annotation(com.keep.redis.advice.RLock)")
    public void lockCut(){}

    @Around("lockCut() && @annotation(lock)")
    public Object logAround(ProceedingJoinPoint joinPoint, RLock lock) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object obj;
        // 获取锁
        String lockName = getLockName(lock);
        org.redisson.api.RLock rLock = SpringContextHolder.getBean(Redisson.class).getLock(lockName);
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

    private String getLockName(RLock lock){
        StringBuilder lockName = new StringBuilder("lock");
        if (Objects.nonNull(lock.name().toString())){
            lockName.append(":").append(lock.name().toString());
        }
        return lockName.toString();
    }
}