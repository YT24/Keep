package com.keep.multdatasource.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/9 16:42
 */

@Component
@ConfigurationProperties(prefix = "spring.datasource.mysql")
@Data
public class MysqlDataSourceProperties {

    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
