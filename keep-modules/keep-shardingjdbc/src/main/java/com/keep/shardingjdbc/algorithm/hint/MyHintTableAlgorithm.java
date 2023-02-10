package com.keep.shardingjdbc.algorithm.hint;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.*;

/**
 * 自定义扩展精确分片算法 DB
 */
public class MyHintTableAlgorithm implements HintShardingAlgorithm<Integer> {


    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Integer> hintShardingValue) {
        String logicTable = hintShardingValue.getLogicTableName();
        Set<String> tables = new HashSet<>();
        for (String table : collection) {
            Collection<Integer> shardingValus = hintShardingValue.getValues();
            if (CollectionUtils.isEmpty(shardingValus)) {
                throw new UnsupportedOperationException("hint sharding value can not be null. please check you config");
            }
            for (Integer shardingValue : shardingValus) {
                if (Objects.equals(table, logicTable +"_"+ shardingValue.toString())) {
                    tables.add(table);
                }
            }
        }
        if (CollectionUtils.isEmpty(tables)) {
            throw new UnsupportedOperationException(" doSharding coolection param is empty. please check you config");
        }
        return tables;

    }
}
