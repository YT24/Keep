package com.keep.multdatasource.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


//@Configuration
//@MapperScan(basePackages = "com.keep.multdatasource.mapper.oracle", sqlSessionTemplateRef = "oracleSqlSessionTemplate")
//@ConfigurationProperties(prefix = "spring.datasource.oracle")
public class OrcaleDataSourceConfig {

    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public static final String MAPPER_CLASSPATH = "classpath:mapper/oracle/*Mapper.xml";

    public static final String MAPPER_PATH = "com.keep.multdatasource.mapper.oracle";


    @Bean("oracleDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource.oracle.enable", value = "true")
    public PooledDataSource getDataSource1() {
        UnpooledDataSource source = new UnpooledDataSource();
        source.setDriver(driverClassName);
        source.setUrl(url);
        source.setUsername(userName);
        source.setPassword(password);
        PooledDataSource pooledDataSource = new PooledDataSource(source);
        return pooledDataSource;
    }

    @Bean(name = "oracleTransactionManager")
    @ConditionalOnProperty(prefix = "spring.datasource.oracle.enable", value = "true")
    public DataSourceTransactionManager dataSourceTransactionManager(PooledDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oracleSqlSessionFactory")
    @ConditionalOnProperty(prefix = "spring.datasource.oracle.enable", value = "true")
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("oracleDataSource") PooledDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        // 指定数据源
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(MAPPER_PATH);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource(MAPPER_CLASSPATH));
        return factoryBean;
    }

    @Bean(name = "oracleSqlSessionTemplate")
    @ConditionalOnProperty(prefix = "spring.datasource.oracle.enable", value = "true")
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

