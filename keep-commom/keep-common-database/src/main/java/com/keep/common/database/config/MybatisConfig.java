package com.keep.common.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan({ "com.keep.**.mapper" })
public class MybatisConfig {


}
