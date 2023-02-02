package com.example.shardingjdbc.algorithm.hint;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.*;

/**
 * 自定义扩展精确分片算法 DB
 */
@Slf4j
public class MyHintDatabaseAlgorithm implements HintShardingAlgorithm<Integer> {

    private String dataSourcePrefix = "g";

    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Integer> hintShardingValue) {
        log.info("MyHintDatabaseAlgorithm param: data_source：{},sharding_value：{},sharding_column_names",collection,hintShardingValue.getValues(),hintShardingValue.getColumnName());
        Set<String> dataSources = new HashSet<>();
        for (String dataSource : collection) {
            Collection<Integer> shardingValus = hintShardingValue.getValues();
            if(CollectionUtils.isEmpty(shardingValus)){
                throw new UnsupportedOperationException("hint sharding value can not be null. please check you config");
            }
            for (Integer shardingValue : shardingValus) {
                if(Objects.equals(dataSource,dataSourcePrefix + shardingValue.toString())){
                    dataSources.add(dataSource);
                }
            }
        }
        return dataSources;
    }
}
