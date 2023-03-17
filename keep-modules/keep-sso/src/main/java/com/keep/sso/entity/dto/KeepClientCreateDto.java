package com.keep.sso.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2023-03-15
 */
@Data
public class KeepClientCreateDto {



    /**
     * 服务名
     */
    @ApiModelProperty("应用名称")
    private String serviceName;

    /**
     * client_id
     */
    @ApiModelProperty("client_id")
    private String clientId;

    /**
     * client_create
     */
    @ApiModelProperty("client_create")
    private String clientSecret;

    /**
     * 协议：cas,oauth2.0,oidc,saml,ltpa
     */
    @ApiModelProperty("协议：cas,oauth2.0,oidc,saml,ltpa")
    private String protocol;


    @ApiModelProperty("应用回调地址")
    private String callbackUrl;

}
