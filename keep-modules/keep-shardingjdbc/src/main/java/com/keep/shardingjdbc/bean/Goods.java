package com.keep.shardingjdbc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Goods {
    //@TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer userId;

    private String status;

}