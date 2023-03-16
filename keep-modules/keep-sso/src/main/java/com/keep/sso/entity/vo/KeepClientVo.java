package com.keep.sso.entity.vo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2023-03-15
 */
@Data
public class KeepClientVo {


    /**
     * 主键
     */
    @ApiModelProperty("应用ID")
      private Integer id;

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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;


}
