package com.keep.readwrite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = "com.keep.readwrite.mapper")
@SpringBootApplication(scanBasePackages = "com.keep.readwrite")
public class ReadWriteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadWriteApplication.class, args);
    }

}
