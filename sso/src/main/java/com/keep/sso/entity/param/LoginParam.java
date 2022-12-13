package com.keep.sso.entity.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginParam {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String passWord;

    @ApiModelProperty("grantType")
    private String grantType;

    @ApiModelProperty("应用ID")
    private String clientId;

    @ApiModelProperty("应用密钥")
    private String clientScrete;
}
