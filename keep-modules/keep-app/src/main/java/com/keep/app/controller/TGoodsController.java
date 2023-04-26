package com.keep.app.controller;


import com.keep.app.service.TGoodsService;
import com.keep.common.core.domain.entity.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
@RequiredArgsConstructor
public class TGoodsController {

    private final TGoodsService tGoodsService;

    public ResponseResult secKill(){
        return ResponseResult.success();
    }

}

