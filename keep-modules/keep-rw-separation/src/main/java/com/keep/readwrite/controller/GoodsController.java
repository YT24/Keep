package com.keep.readwrite.controller;

import com.keep.readwrite.annotation.Master;
import com.keep.readwrite.annotation.Slave;
import com.keep.readwrite.bean.Goods;
import com.keep.readwrite.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GoodsController {

    private final GoodsService goodsService;


    @GetMapping("/list")
    @Slave
    public Object list(){
        System.out.printf("LIST:", goodsService.select());
       return goodsService.list();
    }

    @GetMapping("/save")
    @Master
    public Object save(){
        Goods goods = new Goods();
        goods.setGname("apple");
        goods.setUserId(10L);
        goods.setGstatus("已发布");
       goodsService.save(goods);
        return "SUCCESS";
    }
}
