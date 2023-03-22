package com.keep.sso.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.keep.common.database.BaseEntity;
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
public class KeepRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 角色名称
     */
    private String name;


    private String createBy;


    private String updateBy;



}
