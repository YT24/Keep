package com.example.shardingjdbc.algorithm.standard;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

/**
 * 自定义扩展精确分片算法 TABLE
 */
public class MyPreciseTableAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     *
     * @param avaliableTargetName 可用分片
     * @param preciseShardingValue 分片的查询条件
     * @return
     */
    @Override
    public String doSharding(Collection<String> avaliableTargetName, PreciseShardingValue<Long> preciseShardingValue) {
        // 按照 = 或者 in 进行精确分片
        // 例如 select * from goods where gid = xxxxx or gid in (xxxx,xxxx)
        // 实现 goods_$->{gid % 2 + 1} 分表策略
        BigInteger shardingValue = BigInteger.valueOf(preciseShardingValue.getValue());
        BigInteger res = shardingValue.mod(new BigInteger("2")).add(new BigInteger("1"));
        String key = preciseShardingValue.getLogicTableName()+"_"+res;
        if(avaliableTargetName.contains(key)){
            return key;
        }
        throw new UnsupportedOperationException(" route "+key+" is not support. please check you config");
    }
}
