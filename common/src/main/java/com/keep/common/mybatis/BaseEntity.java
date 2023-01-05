package com.keep.common.mybatis;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class  BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE,value="updated_time")
    private LocalDateTime updatedTime;

    @TableField(fill = FieldFill.INSERT,value="created_time")
    private LocalDateTime createdTime;

    @TableField(value="deleted")
    @TableLogic(value = "NULL",delval="now()")
    private LocalDateTime  deleted;
}
