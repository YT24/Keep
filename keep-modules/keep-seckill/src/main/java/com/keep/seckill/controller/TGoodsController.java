package com.keep.seckill.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.seckill.service.TGoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2023-04-24
 */
@RestController
@RequestMapping("/tGoods")

public class TGoodsController {

    private final TGoodsService tGoodsService;

    public TGoodsController(TGoodsService tGoodsService) {
        this.tGoodsService = tGoodsService;
    }


    @GetMapping("")
    public ResponseResult list(){
        return ResponseResult.success(tGoodsService.list());
    }
}

