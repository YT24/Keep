package com.keep.shardingjdbc.algorithm.complex;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义扩展精确分片算法 DB
 */
public class MyComplexDatabaseAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {


    /**
     *
     * @param avaliableTargetName
     * @param complexKeysShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> avaliableTargetName, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        //实现按照between 进行范围分片
        //select * from goods where user_id in (XXXX,XXX) and gid between xxx and xxx
        Collection<Integer> userId = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get("user_id");
        Range<Integer> gid = complexKeysShardingValue.getColumnNameAndRangeValuesMap().get("gid");
        List<String> res = new ArrayList<>();


        return avaliableTargetName;
    }
}
