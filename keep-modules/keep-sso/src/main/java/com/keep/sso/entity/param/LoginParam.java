package com.keep.sso.entity.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String passWord;

    @ApiModelProperty("grantType")
    private String grantType;

    @ApiModelProperty("应用ID")
    private String clientId;

    @ApiModelProperty("应用密钥")
    private String clientScrete;
}
