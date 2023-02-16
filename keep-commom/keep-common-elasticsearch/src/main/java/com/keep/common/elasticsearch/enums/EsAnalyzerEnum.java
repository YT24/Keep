package com.keep.common.elasticsearch.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EsAnalyzerEnum {
    IK_MAX_WORD("ik_max_word"),
    IK_SMART("ik_smart");

    private final String value;
}