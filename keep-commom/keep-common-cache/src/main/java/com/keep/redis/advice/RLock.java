package com.keep.redis.advice;


import com.keep.common.core.domain.enums.IdTypeEnum;

import java.lang.annotation.*;

/**
 * @author yangte
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RLock {
    /**
     * 锁名
     * @return
     */
    IdTypeEnum name();
}