package com.keep.seckill.domain.enums;

import io.swagger.models.auth.In;

public enum OrderStatusEnum {

    UN_PAY(0),
        PAY(1);

    private Integer status;

    public Integer getStatus(){
        return status;
    }
    OrderStatusEnum(int status) {
        this.status = status;
    }
}
