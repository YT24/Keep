package org.nebula.contrib.ngbatis.annotations;

// Copyright (c) 2022 All project authors. All rights reserved.
//
// This source code is licensed under Apache 2.0 License.


import java.lang.annotation.*;

/**
 * xml中添加space 或者 使用Space注解
 *
 * @author gin soul
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Space {

    /**
     * nebula space name。
     *
     * @return nebula空间名。
     */
    String name();

}
