package com.example.shardingjdbc.algorithm.hint;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * 自定义扩展精确分片算法 DB
 */
public class MyHintTableAlgorithm implements HintShardingAlgorithm<Integer> {


    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Integer> hintShardingValue) {
        String key = "goods_"+hintShardingValue.getValues().toArray()[0].toString();
        if(collection.contains(key)){
            return Arrays.asList(key);
        }
        throw new UnsupportedOperationException(" route "+key+" is not support. please check you config");
    }
}
