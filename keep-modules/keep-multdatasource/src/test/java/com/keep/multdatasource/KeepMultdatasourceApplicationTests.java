package com.keep.multdatasource;

import com.keep.multdatasource.mapper.mysql.UserMysqlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KeepMultdatasourceApplicationTests {

    @Autowired
    private UserMysqlMapper userMysqlMapper;

    @Test
    void contextLoads() {

        System.out.printf("", userMysqlMapper.getById(1));
    }

}
