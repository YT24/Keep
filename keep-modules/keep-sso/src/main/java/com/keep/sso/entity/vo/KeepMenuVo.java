package com.keep.sso.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class KeepMenuVo {

    @ApiModelProperty("ID")
    private Integer id;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("上级菜单")
    private Integer parentId;

    @ApiModelProperty("子菜单")
    private List<KeepMenuVo> children;
}
