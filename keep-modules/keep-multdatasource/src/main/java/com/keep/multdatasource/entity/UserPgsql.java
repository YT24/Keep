package com.keep.multdatasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/8 16:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserPgsql {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;
}
