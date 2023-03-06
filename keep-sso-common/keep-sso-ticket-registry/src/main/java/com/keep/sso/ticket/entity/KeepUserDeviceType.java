package com.keep.sso.ticket.entity;

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
 * @since 2023-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KeepUserDeviceType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * tgt_id
     */
    private String tgtId;


}
