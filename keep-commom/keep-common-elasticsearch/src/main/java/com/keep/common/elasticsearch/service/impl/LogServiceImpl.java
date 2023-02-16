package com.keep.common.elasticsearch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.keep.common.elasticsearch.annotation.EsDocument;
import com.keep.common.elasticsearch.docs.EsLogDoc;
import com.keep.common.elasticsearch.enums.EsAnalyzerEnum;
import com.keep.common.elasticsearch.service.AbstractorIndexService;
import com.keep.common.elasticsearch.service.LogService;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LogServiceImpl extends AbstractorIndexService implements LogService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void create(EsLogDoc esLogDoc) {
        super.createDoc(restHighLevelClient, esLogDoc);
    }

    @SneakyThrows
    @Override
    public List<EsLogDoc> search() {
        final EsDocument annotation = EsLogDoc.class.getAnnotation(EsDocument.class);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(annotation.indexName());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("responseMsg","分词博客").operator(Operator.AND).analyzer(EsAnalyzerEnum.IK_MAX_WORD.getValue()));
        searchRequest.source(sourceBuilder);
        List<EsLogDoc> list = new ArrayList<>();
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            list.add(JSONObject.parseObject(hit.getSourceAsString(),EsLogDoc.class));
        }
        return list;
    }
}
