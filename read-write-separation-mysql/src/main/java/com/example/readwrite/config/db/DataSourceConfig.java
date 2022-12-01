package com.example.readwrite.config.db;

/**
 * @author chaird
 * @create 2020-12-30 21:27
 */
import com.example.readwrite.constants.DBTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 关于数据源配置，参考SpringBoot官方文档第79章《Data Access》 79. Data Access 79.1 Configure a Custom DataSource 79.2
 * Configure Two DataSources
 */
@Configuration
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.master")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.slave")
  public DataSource slaveDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public DataSource myRoutingDataSource(
      @Qualifier("masterDataSource") DataSource masterDataSource,
      @Qualifier("slaveDataSource") DataSource slave1DataSource) {
    Map<Object, Object> targetDataSources = new HashMap<>(2);
    targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
    targetDataSources.put(DBTypeEnum.SLAVE, slave1DataSource);
    MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
    myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
    myRoutingDataSource.setTargetDataSources(targetDataSources);
    return myRoutingDataSource;
  }
}