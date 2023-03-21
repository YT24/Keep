package com.keep.sso.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KeepMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父节点，第一层为0
     */
    private String parentId;

    /**
     * 排序，升序排列，1，2，3
     */
    private Integer menuOrder;

    /**
     * 路由地址
     */
    private String url;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private LocalDateTime deleted;


}
