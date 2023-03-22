package com.keep.sso.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
@Data
public class KeepRoleSaveDto {


    @ApiModelProperty("角色名称")
    private String name;


}
