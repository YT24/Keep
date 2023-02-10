package com.keep.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.shardingjdbc.bean.Goods;
import com.keep.shardingjdbc.mapper.GoodsMapper;
import com.keep.shardingjdbc.service.GoodsService;
import org.springframework.stereotype.Component;

@Component
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>  implements GoodsService {
}
