package com.keep.sso.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class KeepRoleVo {

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("角色名称")
    private String name;

}
