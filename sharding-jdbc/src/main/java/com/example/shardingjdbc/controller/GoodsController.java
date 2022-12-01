package com.example.shardingjdbc.controller;

import com.example.shardingjdbc.bean.Goods;
import com.example.shardingjdbc.maper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

    @Autowired
    private GoodsMapper goodsMapper;


    @GetMapping("/add")
    public String addGoods(){
        Goods good = new Goods();
        good.setGname("小米手机");
        good.setUserId(100L);
        good.setGstatus("已发布");
        goodsMapper.insert(good);

        return "success";
    }
}
