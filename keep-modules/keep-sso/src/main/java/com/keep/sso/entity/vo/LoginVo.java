package com.keep.sso.entity.vo;

import lombok.Data;

@Data
public class LoginVo {

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;

    private String tokenType = "bearer";
}
