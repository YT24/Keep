package com.keep.shardingjdbc.algorithm.standard;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

/**
 * 自定义扩展精确分片算法 DB
 */
@Slf4j
public class MyPreciseDatabaseAlgorithm implements PreciseShardingAlgorithm<Integer> {

    private static final String DATA_SOURCE_PREF = "g";

    private BigInteger MOD = new BigInteger("2");


    /**
     *
     * @param dataSources 可用分片
     * @param preciseShardingValue 分片的查询条件
     * @return
     */
    @Override
    public String doSharding(Collection<String> dataSources, PreciseShardingValue<Integer> preciseShardingValue) {
        // 按照 = 或者 in 进行精确分片
        // 例如 select * from goods where gid = xxxxx or gid in (xxxx,xxxx)
        // 实现 g$->{user_id % 2}
        BigInteger shardingValue = BigInteger.valueOf(preciseShardingValue.getValue());
        BigInteger result = shardingValue.mod(MOD).add(new BigInteger("1"));
        String key = DATA_SOURCE_PREF+result;
        if(dataSources.contains(key)){
            log.info("database is : {}",key);
            return key;
        }
        throw new UnsupportedOperationException(" route "+key+" is not support. please check you config");
    }
}
