package com.keep.sso.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class KeepOperationVo {

    @ApiModelProperty("操作ID")
    private Integer operId;


    @ApiModelProperty("操作名称")
    private String operName;

}
