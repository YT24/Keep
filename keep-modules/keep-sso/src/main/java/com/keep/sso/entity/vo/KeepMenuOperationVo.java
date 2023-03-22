package com.keep.sso.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class KeepMenuOperationVo {

    @ApiModelProperty("ID")
    private Integer id;


    @ApiModelProperty("菜单名称")
    private String name;


    @ApiModelProperty("菜单操作项")
    private List<KeepOperationVo> operations;
}
