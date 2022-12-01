package com.example.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;



@MapperScan("com.example.shardingjdbc.maper")
@SpringBootApplication(scanBasePackages = "com.example.shardingjdbc",exclude = {
//        DataSourceAutoConfiguration.class,
//        org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.class
})
public class ShardingJDBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJDBCApplication.class, args);
    }

}
