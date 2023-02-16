package com.keep.kafka.kafka;

import com.keep.common.elasticsearch.docs.EsLogDoc;
import com.keep.common.elasticsearch.service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class KafkaApplicationTests {


    @Autowired
    private LogService logService;

    @Test
    void contextLoads() {
        EsLogDoc esLogDoc = new EsLogDoc();
        esLogDoc.setId(UUID.randomUUID().toString());
        esLogDoc.setCreatedTime(LocalDateTime.now());
        esLogDoc.setUpdatedTime(LocalDateTime.now());
        esLogDoc.setUsername("AAAA");
        esLogDoc.setRequestUrl("/API/LOGIN");
        esLogDoc.setCostTime(0.034);
        esLogDoc.setResponseMsg("...查询、分词查询、精确查询_xumengd的博客-CSDN博客_es ...\n" +
                "2020年4月27日 sourceBuilder.query(boolQueryBuilder); 遇到的坑:wildcardQuery的模糊查询,在es中属性类型type=text时仍然是分词的形式查询,只有在type=keyword时,wildcardQ...\n" +
                "CSDN博客\uE62B");
        logService.create(esLogDoc);
        List<EsLogDoc> search = logService.search();
        for (EsLogDoc logDoc : search) {
            System.out.println(logDoc);
        }
    }

}