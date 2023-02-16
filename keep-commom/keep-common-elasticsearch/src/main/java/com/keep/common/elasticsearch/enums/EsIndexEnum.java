package com.keep.common.elasticsearch.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EsIndexEnum {

    /**
     * 日志记录索引
     */
    LOG("keep_log_index");

    private String index;

}
