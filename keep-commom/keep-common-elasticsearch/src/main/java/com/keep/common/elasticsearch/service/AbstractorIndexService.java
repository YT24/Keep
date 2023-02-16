package com.keep.common.elasticsearch.service;


import com.alibaba.fastjson.JSONObject;
import com.keep.common.core.expection.EsExpection;
import com.keep.common.elasticsearch.annotation.EsDocument;
import com.keep.common.elasticsearch.docs.EsBaseDoc;
import com.keep.common.elasticsearch.docs.EsLogDoc;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class AbstractorIndexService<T extends EsBaseDoc> {


    public void createDoc(RestHighLevelClient restHighLevelClient, T t){
        final EsDocument annotation = t.getClass().getAnnotation(EsDocument.class);
        IndexRequest indexRequest = new IndexRequest(annotation.indexName());
        indexRequest.id(t.getId());
        indexRequest.source(JSONObject.toJSONString(t), XContentType.JSON);
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es操作插入文档异常 : {}", e);
            throw new EsExpection("es操作插入文档异常:" + e.getMessage());
        }
    }

    public void deleteDoc(RestHighLevelClient restHighLevelClient,T t)  {
        final EsDocument annotation = t.getClass().getAnnotation(EsDocument.class);
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index(annotation.indexName());
        deleteRequest.id(t.getId());
        deleteRequest.timeout(TimeValue.timeValueSeconds(30));
        deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es操作删除文档异常 : {}", e);
            throw new EsExpection("es操作删除文档异常:" + e.getMessage());
        }
    }

}
