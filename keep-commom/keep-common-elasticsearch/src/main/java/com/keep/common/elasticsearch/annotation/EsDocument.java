package com.keep.common.elasticsearch.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsDocument {
    String indexName();
    int shards() default 0;
    int replicas() default 0;
}