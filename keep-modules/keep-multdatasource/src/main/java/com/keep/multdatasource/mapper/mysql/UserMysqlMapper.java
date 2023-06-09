package com.keep.multdatasource.mapper.mysql;

import com.keep.multdatasource.entity.UserMysql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/8 16:20
 */
@Mapper
public interface UserMysqlMapper {

    UserMysql getById(@Param("id") Integer id);

}
