package com.example.shardingjdbc.algorithm.standard;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义扩展范围分片算法 DB
 */
public class MyRangeTableAlgorithm implements RangeShardingAlgorithm<Integer> {

    private static final BigInteger MOD = new BigInteger("2");

    private static final String UNDERLINE = "_";


    /**
     *
     * @param tableNames 可用分片
     * @param rangeShardingValue 范围的查询条件  包含逻辑表分片列 和分片列的范围条件
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> tableNames, RangeShardingValue<Integer> rangeShardingValue) {
        if(!rangeShardingValue.getValueRange().hasLowerBound() || !rangeShardingValue.getValueRange().hasUpperBound()){
            return tableNames;
        }
        Integer lowerEndpoint = rangeShardingValue.getValueRange().lowerEndpoint();
        Integer upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();

        if(lowerEndpoint > upperEndpoint){
            throw new UnsupportedOperationException("error param of range");
        }
        
        // 奇偶分离的场景大部分范围查询都是要查两张表
        Set<String> rounteTableNames = new HashSet<>();
        for (int i = lowerEndpoint; i <= upperEndpoint ; i++) {
            BigInteger result = new BigInteger(String.valueOf(i)).mod(MOD).add(new BigInteger("2"));
            String logicTableName = rangeShardingValue.getLogicTableName();
            String routeTableName = logicTableName + UNDERLINE + result;
            if(tableNames.contains(routeTableName)){
                rounteTableNames.add(routeTableName);
            }
        }
        if (CollectionUtils.isEmpty(rounteTableNames)){
            throw new UnsupportedOperationException(" route tables "+rounteTableNames+" is empty. please check you config");
        }
        return rounteTableNames;
    }
}
