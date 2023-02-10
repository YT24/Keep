package com.keep.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {



    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        if(Objects.isNull(this.getFieldValByName("createdTime", metaObject))){
            this.setFieldValByName("createdTime", now, metaObject);
        };
        if(Objects.isNull(this.getFieldValByName("updatedTime", metaObject))){
            this.setFieldValByName("updatedTime", now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("updatedTime", now, metaObject);
    }

}