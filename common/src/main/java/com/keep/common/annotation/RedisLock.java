package com.keep.common.annotation;


import com.keep.common.enums.IdTypeEnum;

import java.lang.annotation.*;

/**
 * @author yangte
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {
    /**
     * 锁名
     * @return
     */
    IdTypeEnum name();
}