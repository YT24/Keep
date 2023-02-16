package com.keep.common.elasticsearch.docs;

import com.keep.common.elasticsearch.annotation.EsDocument;
import com.keep.common.elasticsearch.annotation.EsField;
import com.keep.common.elasticsearch.enums.EsAnalyzerEnum;
import com.keep.common.elasticsearch.enums.EsFieldTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EsDocument(indexName = "keep_log_index",shards = 1,replicas = 0)
public class EsLogDoc extends EsBaseDoc {


    @EsField(type = EsFieldTypeEnum.KEYWORD)
    private String username;

    @EsField(type = EsFieldTypeEnum.KEYWORD)
    private String requestUrl;

    @EsField(type = EsFieldTypeEnum.DOUBLE)
    private Double costTime;

    @EsField(type = EsFieldTypeEnum.TEXT,analyzer = EsAnalyzerEnum.IK_MAX_WORD)
    private String responseMsg;


}
