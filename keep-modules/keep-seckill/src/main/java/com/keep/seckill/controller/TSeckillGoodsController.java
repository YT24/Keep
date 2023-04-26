package com.keep.seckill.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.seckill.domain.dto.UserSecKillDto;
import com.keep.seckill.service.TSeckillGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2023-04-24
 */
@RestController
@RequestMapping("/tSeckillGoods")
@RequiredArgsConstructor
public class TSeckillGoodsController {

    private final TSeckillGoodsService tSeckillGoodsService;



    @PostMapping()
    public ResponseResult seckill(@RequestBody UserSecKillDto userSecKillDto) throws Exception {
        tSeckillGoodsService.doKill(userSecKillDto);
        return ResponseResult.success();
    }

}

