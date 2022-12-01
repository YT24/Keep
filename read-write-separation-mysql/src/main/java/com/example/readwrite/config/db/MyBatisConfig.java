package com.example.readwrite.config.db;

/**
 * @author YT
 * @create 2
 */
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
public class MyBatisConfig {

  @Resource(name = "myRoutingDataSource")
  private DataSource myRoutingDataSource;

  public static final String MAPPER_PACKAGE = "com.example.readwrite.mapper";
  public static final String TYPE_ALIASES_PACKAGE = "com.example.readwrite.bean";
  public static final String MAPPER_XML_LOCATIONS = "classpath:mapper/*.xml";

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(myRoutingDataSource);
    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    configuration.setMapUnderscoreToCamelCase(true);
    configuration.setCacheEnabled(false);
    sqlSessionFactory.setConfiguration(configuration);
    return sqlSessionFactory.getObject();
  }


  @Bean
  public PlatformTransactionManager platformTransactionManager() {
    return new DataSourceTransactionManager(myRoutingDataSource);
  }
}