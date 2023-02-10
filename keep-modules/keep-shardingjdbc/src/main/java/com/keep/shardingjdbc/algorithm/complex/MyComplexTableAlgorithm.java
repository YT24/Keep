package com.keep.shardingjdbc.algorithm.complex;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义扩展精确分片算法 TABLE
 */
public class MyComplexTableAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {


    /**
     *
     * @param routeTables
     * @param complexKeysShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> routeTables, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        //实现按照between 进行范围分片
        //select * from goods where user_id in (XXXX,XXX) and gid between xxx and xxx
//        Collection<Integer> gid = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get("gid");
//        Range<Integer> userId = complexKeysShardingValue.getColumnNameAndRangeValuesMap().get("user_id");
//        List<String> tables = new ArrayList<>();
//
//        Integer lowerEndpoint = userId.lowerEndpoint();
//        Integer upperEndpoint = userId.upperEndpoint();
//
//        if(lowerEndpoint > upperEndpoint){
//            throw new UnsupportedOperationException("error param of range");
//        }
//
//        for (Integer id : gid) {
//            BigInteger shardingValue = BigInteger.valueOf(id);
//            BigInteger res = shardingValue.mod(new BigInteger("2")).add(new BigInteger("1"));
//            String key = complexKeysShardingValue.getLogicTableName()+"_"+res;
//            tables.add(key);
//        }



        return routeTables;
    }
}
