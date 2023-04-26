package com.keep.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.keep.shardingjdbc.bean.Goods;
import com.keep.shardingjdbc.mapper.GoodsMapper;
import com.keep.shardingjdbc.mapper.UserMapper;
import com.keep.shardingjdbc.service.GoodsService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@MapperScan("com.example.shardingjdbc.maper")
@SpringBootTest
class ShardingJDBCApplicationTests {


    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserMapper userMapper;

    // INLINE 策略
    @Test
    void addGoods() {
        /*for (int i = 1; i <= 10; i++) {
            Goods good = new Goods();
            good.setId((long) i);
            good.setName("三星" + i);
            good.setUserId(i);
            good.setStatus("已发布");
            goodsMapper.insert(good);
        }*/
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        //queryWrapper.lambda().eq(Goods::getUserId,1);
        queryWrapper.lambda().between(Goods::getUserId,1,5);
        queryWrapper.lambda().eq(Goods::getId,5);
        System.out.println(this.goodsMapper.selectList(queryWrapper));
    }

    @Test
    void addUser() {
//        User user = new User();
//        user.setStatus(Boolean.TRUE);
//        user.setUsername("james");
//        user.setPassword("23");
//        userMapper.insert(user);

    }


    @Test
    void hint(){
        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("goods",1);
        hintManager.addTableShardingValue("goods",1);
//        List<Goods> goodsList = goodsMapper.selectList(null);
//        goodsList.forEach(goods -> System.out.println(goods));
        Goods good = new Goods();
        good.setName("三星");
        good.setUserId(1);
        good.setStatus("已发布");
        goodsMapper.insert(good);
        hintManager.close();
    }


    @Test
    void standard(){
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Goods good = new Goods();
            good.setName("APPLE"+i+1);
            good.setUserId(1+i);
            good.setStatus("已发售");
            goodsList.add(good);
        }
        //goodsService.saveBatch(goodsList);
        QueryWrapper<Goods> query = new QueryWrapper<>();
        //query.lambda().in(Goods::getUserId, Arrays.asList(new Integer[]{1,3}));
        query.lambda().between(Goods::getUserId,1,4);
        //query.lambda().lt(Goods::getUserId,2);
        List<Goods> goods = goodsMapper.selectList(query);
        System.out.println(goods);
    }

}
