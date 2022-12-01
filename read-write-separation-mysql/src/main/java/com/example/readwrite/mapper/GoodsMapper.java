package com.example.readwrite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.readwrite.bean.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

}