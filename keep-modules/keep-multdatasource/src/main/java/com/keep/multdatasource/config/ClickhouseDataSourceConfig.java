package com.keep.multdatasource.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(value = "com.keep.multdatasource.mapper.clickhouse", sqlSessionFactoryRef = "clickhouseSqlSessionFactory")
public class ClickhouseDataSourceConfig {

    @Value("${spring.datasource.clickhouse.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.clickhouse.url}")
    private String url;

    @Value("${spring.datasource.clickhouse.username}")
    private String userName;

    @Value("${spring.datasource.clickhouse.password}")
    private String password;

    public static final String MAPPER_CLASSPATH = "classpath:mapper/clickhouse/*Mapper.xml";

    public static final String MAPPER_PATH = "com.keep.multdatasource.mapper.clickhouse";


    @Bean("clickhouseDataSource")
    public PooledDataSource getDataSource1() {
        UnpooledDataSource source = new UnpooledDataSource();
        source.setDriver(driverName);
        source.setUrl(url);
        //source.setUsername(userName);
        //source.setPassword(password);
        PooledDataSource pooledDataSource = new PooledDataSource(source);
        return pooledDataSource;
    }

    @Bean(name = "clickhouseTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("clickhouseDataSource") PooledDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "clickhouseSqlSessionFactory")
    public MybatisSqlSessionFactoryBean clickhouseSqlSessionFactoryBean(@Qualifier("clickhouseDataSource") PooledDataSource dataSource) throws Exception {
        //SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        // 指定数据源
        factoryBean.setDataSource(dataSource);
        // 指定mapper xml路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperXml = resolver.getResources(MAPPER_CLASSPATH);
        factoryBean.setTypeAliasesPackage(MAPPER_PATH);
        factoryBean.setMapperLocations(mapperXml);
        return factoryBean;
    }

    @Bean(name = "clickhouseSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("clickhouseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

