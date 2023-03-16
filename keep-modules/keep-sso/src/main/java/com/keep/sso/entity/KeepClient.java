package com.keep.sso.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
public class KeepClient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      private Integer id;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * client_id
     */
    private String clientId;

    /**
     * client_create
     */
    private String clientSecret;

    /**
     * callback_url
     */
    private String callbackUrl;

    /**
     * 协议：cas,oauth2.0,oidc,saml,ltpa
     */
    private String protocol;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否停用
     */
    private Boolean status;


}
