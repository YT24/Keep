package com.example.readwrite.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.readwrite.bean.Goods;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodsService extends IService<Goods> {


    List<Goods> select();

}


