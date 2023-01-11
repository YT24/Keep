package com.keep.readwrite;

import com.keep.readwrite.annotation.Master;
import com.keep.readwrite.bean.Goods;
import com.keep.readwrite.mapper.GoodsMapper;
import com.keep.readwrite.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@MapperScan("com.example.readwrite.mapper")
@SpringBootTest
public class ReadWriteApplicationTests {


    @Autowired
    GoodsService goodsService;


    @Test
    public void insert() {
        System.out.println(goodsService.list());


//                Goods good = new Goods();
//        good.setGname("三星");
//        good.setUserId(2010L);
//        good.setGstatus("已发布");
//        goodsService.save(good);good
    }


}
