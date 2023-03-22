package com.keep.common.database;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class  BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE,value="update_time")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT,value="create_time")
    private LocalDateTime createTime;

    @TableField(value="deleted")
    @TableLogic(value = "NULL",delval="now()")
    private LocalDateTime  deleted;
}
