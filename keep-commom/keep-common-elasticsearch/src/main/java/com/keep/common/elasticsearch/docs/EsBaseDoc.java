package com.keep.common.elasticsearch.docs;

import com.keep.common.elasticsearch.annotation.EsField;
import com.keep.common.elasticsearch.enums.EsFieldTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EsBaseDoc {

    @EsField(type = EsFieldTypeEnum.KEYWORD)
    private String id;

    @EsField(type = EsFieldTypeEnum.DATE)
    private LocalDateTime createdTime;

    @EsField(type = EsFieldTypeEnum.DATE)
    private LocalDateTime updatedTime;
}
