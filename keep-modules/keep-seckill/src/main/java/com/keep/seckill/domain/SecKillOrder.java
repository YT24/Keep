package com.keep.seckill.domain;

import org.springframework.context.ApplicationEvent;

public class SecKillOrder extends ApplicationEvent {

    private Long userId;

    private Long goodsId;

    public SecKillOrder(Long userId,Long goodsId) {
        super(userId);
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }
}
