package com.keep.readwrite.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.readwrite.annotation.Master;
import com.keep.readwrite.bean.Goods;
import com.keep.readwrite.mapper.GoodsMapper;
import com.keep.readwrite.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {



    @Override
    public List<Goods> select() {

        return this.lambdaQuery().list();
    }
}
