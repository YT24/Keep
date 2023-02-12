package com.keep.sso.entity;

import java.io.Serializable;

import com.keep.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String realName;

    private String mobile;

    private String email;

    private Boolean mustChangePassword;

}
