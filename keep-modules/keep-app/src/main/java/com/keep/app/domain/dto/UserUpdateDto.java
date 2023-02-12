package com.keep.app.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2023-02-12
 */
@Data
public class UserUpdateDto {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    private Long id;

    @Size(min = 0,max = 25)
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户名")
    private String password;

    @ApiModelProperty("用户名")
    private String realName;

    @ApiModelProperty("用户名")
    private String mobile;

    @ApiModelProperty("用户名")
    private String email;


}
