package com.keep.common.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseEntity {
    @TableId
    private String id;
    @TableField(fill = FieldFill.INSERT_UPDATE,value="updated_time")
    private LocalDateTime updatedTime;
    @TableField(fill = FieldFill.INSERT,value="created_time")
    private LocalDateTime createdTime;
    @TableField(fill = FieldFill.INSERT_UPDATE,value="updated_by")
    private String updatedBy;
    @TableField(fill = FieldFill.INSERT,value="created_by")
    private String createdBy;
    @TableField(value="deleted")
    @TableLogic(value = "NULL",delval="now()")
    private LocalDateTime  deleteTime;
}
