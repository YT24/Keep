package com.keep.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.keep.shardingjdbc.mapper")
@SpringBootApplication(scanBasePackages = "com.keep.shardingjdbc",exclude = {
//        DataSourceAutoConfiguration.class,
//        org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.class
})
public class ShardingJDBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJDBCApplication.class, args);
    }

}
