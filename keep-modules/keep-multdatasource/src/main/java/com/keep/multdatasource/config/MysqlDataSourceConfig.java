package com.keep.multdatasource.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.keep.multdatasource.config.properties.MysqlDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(value = "com.keep.multdatasource.mapper.mysql", sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlDataSourceConfig {

    @Autowired
    private MysqlDataSourceProperties mysqlDataSourceProperties;
    public static final String MAPPER_CLASSPATH = "classpath:mapper/mysql/*Mapper.xml";

    public static final String MAPPER_PATH = "com.keep.multdatasource.mapper.mysql";


    @Bean("mysqlDataSource")
    public HikariDataSource mysqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(mysqlDataSourceProperties.getDriverClassName());
        dataSource.setJdbcUrl(mysqlDataSourceProperties.getUrl());
        dataSource.setUsername(mysqlDataSourceProperties.getUsername());
        dataSource.setPassword(mysqlDataSourceProperties.getPassword());
        return dataSource;
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") HikariDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        // 指定数据源 // 指定mapper xml路径
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(MAPPER_PATH);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_CLASSPATH));
        return factoryBean.getObject();
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    @ConditionalOnProperty(prefix = "spring.datasource.mysql.enable", value = "true")
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

