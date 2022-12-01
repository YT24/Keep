package com.example.shardingjdbc.algorithm.standard;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.math.BigInteger;
import java.util.Collection;

/**
 * 自定义扩展精确分片算法 DB
 */
public class MyRangeDatabaseAlgorithm implements RangeShardingAlgorithm<Long> {

    /**
     *
     * @param avaliableTargetName 可用分片
     * @param rangeShardingValue 范围的查询条件  包含逻辑表分片列 和分片列的范围条件
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> avaliableTargetName, RangeShardingValue<Long> rangeShardingValue) {

        Long lowerEndpoint = rangeShardingValue.getValueRange().lowerEndpoint();
        Long upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();
        // 奇偶分离的场景大部分范围查询都是要查两张表
        // 直接返回
        return avaliableTargetName;
    }
}
