package com.keep.common.elasticsearch.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: es字段数据类型
 * @Create: LiFei
 * @Date: 2021/7/2/2:43 下午
 */
@Getter
@AllArgsConstructor
public enum EsFieldTypeEnum {
    DATE("date"),
    KEYWORD("keyword"),
    INTEGER("integer"),
    LONG("long"),
    DOUBLE("double"),
    FLOAT("float"),
    TEXT("text"),
    BOOLEAN("boolean"),
    SHORT("short");

    private final String code;
}
