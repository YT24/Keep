package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.bean.Goods;
import com.example.shardingjdbc.mapper.GoodsMapper;
import com.example.shardingjdbc.service.GoodsService;
import org.springframework.stereotype.Component;

@Component
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>  implements GoodsService {
}
