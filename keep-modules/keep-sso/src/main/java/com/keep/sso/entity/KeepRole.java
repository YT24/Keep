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
public class KeepRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
      private Integer id;

    /**
     * 角色名称
     */
    private String name;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private LocalDateTime deleted;


}
