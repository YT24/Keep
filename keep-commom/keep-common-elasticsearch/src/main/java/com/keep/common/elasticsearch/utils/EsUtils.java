package com.keep.common.elasticsearch.utils;


import com.keep.common.elasticsearch.annotation.EsDocument;
import com.keep.common.elasticsearch.annotation.EsField;
import com.keep.common.elasticsearch.enums.EsAnalyzerEnum;
import com.keep.common.elasticsearch.enums.EsFieldTypeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class EsUtils {


    @SneakyThrows
    public static void buildMappings(CreateIndexRequest indexRequest, Class clazz) {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.startObject("properties");
        Field[] fields = getAllDeclaredFields(clazz);
        for (Field f : fields) {
            if (f.isAnnotationPresent(EsField.class)) {
                EsField[] declaredAnnotationsByType = f.getDeclaredAnnotationsByType(EsField.class);
                EsFieldTypeEnum type = declaredAnnotationsByType[0].type();
                EsAnalyzerEnum analyzer = declaredAnnotationsByType[0].analyzer();
                builder.startObject(f.getName());
                builder.field("type", type.getCode());
                if(Objects.equals(type,EsFieldTypeEnum.TEXT)){
                    Map<String, Map<String, Object>> ikFields = new HashMap<>();
                    Map<String, Object> keyword = new HashMap<>();
                    keyword.put("type", EsFieldTypeEnum.KEYWORD.getCode());
                    keyword.put("ignore_above", 256);
                    ikFields.put("keyword",keyword);
                    builder.field("fields", ikFields).field("analyzer", analyzer.getValue());
                }
                builder.endObject();
            }
        }
        builder.endObject();
        builder.endObject();
        indexRequest.mapping(builder);
    }

    public static <T> Field[] getAllDeclaredFields(Class<T> clazz) {
        List<Field[]> fieldArrayList = new ArrayList<Field[]>();
        while (clazz != null) {
            fieldArrayList.add(clazz.getDeclaredFields());
            clazz = (Class<T>) clazz.getSuperclass();
        }
        int fieldCount = 0;
        int fieldIndex = 0;
        for (Field[] fieldArray : fieldArrayList) {
            fieldCount = fieldCount + fieldArray.length;
        }
        Field[] allFields = new Field[fieldCount];
        for (Field[] fieldArray : fieldArrayList) {
            for (Field field : fieldArray) {
                allFields[fieldIndex++] = field;
            }
        }
        return allFields;
    }

    public static void buildSetting(CreateIndexRequest request, Class clazz) {
        final EsDocument annotation = (EsDocument) clazz.getAnnotation(EsDocument.class);
        Settings.Builder builder = Settings.builder();
        builder.put("index.number_of_shards", annotation.shards());
        builder.put("index.number_of_replicas", annotation.replicas());
        request.settings(builder);
    }

    @SneakyThrows
    public static void createIndexRequest(RestHighLevelClient restHighLevelClient, Class clazz) {
        final EsDocument annotation = (EsDocument) clazz.getAnnotation(EsDocument.class);
        if (Objects.isNull(annotation)) {
            return;
        }
        GetIndexRequest request = new GetIndexRequest(annotation.indexName());
        boolean exist = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        if (exist) {
            log.error("索引：{} 已存在！！！", annotation.indexName());
            return;
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(annotation.indexName());
        buildSetting(createIndexRequest, clazz);
        buildMappings(createIndexRequest, clazz);
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        log.info("索引：{} 创建成功！！！", annotation.indexName());


    }


}
