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
 * @since 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KeepRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
      private Integer id;

    /**
     * 菜单ID或者操作项ID
     */
    private Integer permissionId;

    /**
     * 权限类型 menu, operation
     */
    private String permissionType;

    /**
     * 角色ID
     */
    private Integer roleId;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private LocalDateTime deleted;


}
