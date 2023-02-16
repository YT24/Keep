package com.keep.common.elasticsearch.service;

import com.keep.common.elasticsearch.docs.EsLogDoc;

import java.util.List;

public interface LogService {

    void create(EsLogDoc esLogDoc);

    List<EsLogDoc> search();



}
