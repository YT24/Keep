package com.keep.keepnebula;

import com.keep.keepnebula.dao.AssetsGraphInitDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Slf4j
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class},
        scanBasePackages = {"com.keep.keepnebula", "org.nebula.contrib"}
)
public class KeepNebulaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KeepNebulaApplication.class, args);
    }


    @Autowired
    private AssetsGraphInitDao assetsGraphInitDao;

    @Value("${nebula.partition_num:10}")
    private Integer partitionNum;

    @Value("${nebula.replica_factor:1}")
    private Integer replicaFactor;

    //@EventListener
    public void ApplicationReadyEvent(ApplicationReadyEvent applicationReadyEvent) {
        assetsGraphInitDao.insertTag();
        assetsGraphInitDao.insertEdge();
        assetsGraphInitDao.insertIndex();
        System.out.println("---------------------------nebula 图空间 assets_graph 初始化完成------------------------------");
    }
}
