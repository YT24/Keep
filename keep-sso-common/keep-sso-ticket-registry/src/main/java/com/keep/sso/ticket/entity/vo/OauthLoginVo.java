package com.keep.sso.ticket.entity.vo;

import lombok.Data;

@Data
public class OauthLoginVo {

    private String accessToken;

    private String refreshToken;

    private Long expiredIn;
}
