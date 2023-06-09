package com.keep.multdatasource.controller;

import com.keep.multdatasource.mapper.clickhouse.UserClickhouseMapper;
import com.keep.multdatasource.mapper.mysql.UserMysqlMapper;
import com.keep.multdatasource.mapper.pgsql.UserPgsqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/8 16:46
 */
@RestController
public class MultDataSourceController {

    @Autowired
    private UserMysqlMapper userMysqlMapper;

    @Autowired
    private UserPgsqlMapper userPgsqlMapper;

    @Autowired
    private UserClickhouseMapper userClickhouseMapper;

    @GetMapping("/user/{id}")
    public Object contextLoads(@PathVariable("id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("mysql", userMysqlMapper.getById(1));
        result.put("clickhouse", userClickhouseMapper.getById(1));
        result.put("pgsql", userPgsqlMapper.getById(1));
        return result;
    }
}
