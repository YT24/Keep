package com.keep.liquibase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author yangte
 * @description TODO
 * @date 2023/7/9 20:36
 */
@SpringBootApplication
public class KeepLiquibaseApplication {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KeepLiquibaseApplication.class, args);
    }

    @EventListener
    public void applicationReadyEvent(ApplicationReadyEvent event) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from test_liquibase");
        System.out.println("maps:" + maps);
    }
}
