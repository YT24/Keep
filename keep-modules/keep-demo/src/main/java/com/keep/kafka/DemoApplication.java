package com.keep.kafka;

import com.keep.common.elasticsearch.utils.EsUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;

@Slf4j
@ComponentScan(basePackages = "com.keep")
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }




    @Autowired
    RestHighLevelClient restHighLevelClient;

    @SneakyThrows
    @EventListener
    public void deploymentVer(ApplicationReadyEvent event) throws IOException {
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:com/keep/common/elasticsearch/docs/*.class");
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        for (Resource resource : resources) {
            MetadataReader reader = cachingMetadataReaderFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class aClass = loader.loadClass(className);
            EsUtils.createIndexRequest(restHighLevelClient,aClass);
        }
        log.info("es index 初始化完成 ～～～");
    }



}
