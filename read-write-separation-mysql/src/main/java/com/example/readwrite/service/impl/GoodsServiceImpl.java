package com.example.readwrite.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.readwrite.annotation.Master;
import com.example.readwrite.bean.Goods;
import com.example.readwrite.mapper.GoodsMapper;
import com.example.readwrite.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {



    @Override
    public List<Goods> select() {

        return this.lambdaQuery().list();
    }
}
