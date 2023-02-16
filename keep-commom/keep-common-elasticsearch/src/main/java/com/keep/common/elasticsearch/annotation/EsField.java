package com.keep.common.elasticsearch.annotation;

import com.keep.common.elasticsearch.enums.EsAnalyzerEnum;
import com.keep.common.elasticsearch.enums.EsFieldTypeEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface EsField {

    /**
     * 字段类型
     * @return
     */
    EsFieldTypeEnum type() default EsFieldTypeEnum.TEXT;

    /**
     * 指定分词器
     * @return
     */
    EsAnalyzerEnum analyzer() default EsAnalyzerEnum.IK_MAX_WORD;

}
