package com.keep.multdatasource.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(value = "com.keep.multdatasource.mapper.pgsql", sqlSessionFactoryRef = "pgsqlSqlSessionFactory")
public class PgsqlDataSourceConfig {

    @Value("${spring.datasource.pgsql.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.pgsql.url}")
    private String url;

    @Value("${spring.datasource.pgsql.username}")
    private String userName;

    @Value("${spring.datasource.pgsql.password}")
    private String password;

    public static final String MAPPER_CLASSPATH = "classpath:mapper/pgsql/*Mapper.xml";

    public static final String MAPPER_PATH = "com.keep.multdatasource.mapper.pgsql";


    @Bean("pgsqlDataSource")
    public HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "pgsqlTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("pgsqlDataSource") HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "pgsqlSqlSessionFactory")
    public MybatisSqlSessionFactoryBean pgsqlSessionFactoryBean(@Qualifier("pgsqlDataSource") HikariDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        // 指定数据源 // 指定mapper xml路径
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(MAPPER_PATH);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_CLASSPATH));
        return factoryBean;
    }

    @Bean(name = "pgsqlSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("pgsqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

