package com.keep.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.keep"})
@EnableFeignClients(basePackages = "com.keep.**.fegin")
@MapperScan(basePackages = "com.keep.app.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class,args);
    }
}
