package com.keep.seckill.domain.dto;

import lombok.Data;

@Data
public class UserSecKillDto {

    /**
     *用户ID
     */
    private Long userId;

    /**
     * 秒杀商品ID
     */
    private Long goodsId;
}
