package com.keep.multdatasource.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/9 16:53
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.pgsql")
@Data
public class PgsqlDataSourceProperties extends MysqlDataSourceProperties {
}
