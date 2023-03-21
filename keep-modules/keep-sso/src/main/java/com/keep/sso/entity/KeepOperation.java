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
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KeepOperation implements Serializable {

    private static final long serialVersionUID = 1L;

      private String id;

    private String name;

    private String operationUrl;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private LocalDateTime deleted;


}
