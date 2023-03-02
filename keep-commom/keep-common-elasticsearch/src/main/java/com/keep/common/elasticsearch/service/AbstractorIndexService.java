package com.keep.common.elasticsearch.service;


import com.alibaba.fastjson.JSONObject;
import com.keep.common.core.expection.EsExpection;
import com.keep.common.elasticsearch.annotation.EsDocument;
import com.keep.common.elasticsearch.docs.EsBaseDoc;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

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
