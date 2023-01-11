package com.keep.shardingjdbc;

import com.keep.shardingjdbc.bean.Goods;
import com.keep.shardingjdbc.bean.User;
import com.keep.shardingjdbc.maper.GoodsMapper;
import com.keep.shardingjdbc.maper.UserMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@MapperScan("com.example.shardingjdbc.maper")
@SpringBootTest
class ShardingJDBCApplicationTests {


    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserMapper userMapper;

    // INLINE 策略
    @Test
    void addGoods() {
        Goods good = new Goods();
        good.setGname("三星");
        good.setUserId(2010L);
        good.setGstatus("已发布");
        goodsMapper.insert(good);

        Goods good2 = new Goods();
        good2.setGname("苹果");
        good2.setUserId(2011L);
        good2.setGstatus("已发布");
        goodsMapper.insert(good2);

    }

    @Test
    void addUser() {
        User user = new User();
        user.setStatus(Boolean.TRUE);
        user.setUsername("kobe");
        user.setPassword("24");
        userMapper.insert(user);
    }


    @Test
    void hint(){
        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("goods","1");
        hintManager.addTableShardingValue("goods","1");
        List<Goods> goodsList = goodsMapper.selectList(null);
        goodsList.forEach(goods -> System.out.println(goods));
        hintManager.close();
    }

}
