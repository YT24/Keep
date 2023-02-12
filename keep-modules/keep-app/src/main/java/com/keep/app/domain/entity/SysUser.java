package com.keep.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-02-12
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


    private Integer deptId;



}
