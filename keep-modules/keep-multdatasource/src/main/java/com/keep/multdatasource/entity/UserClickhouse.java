package com.keep.multdatasource.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/8 16:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserClickhouse {

    private Integer id;

    private String userName;
}
