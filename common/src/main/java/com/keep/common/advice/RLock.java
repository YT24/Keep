package com.keep.common.advice;


import com.keep.common.enums.IdTypeEnum;

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