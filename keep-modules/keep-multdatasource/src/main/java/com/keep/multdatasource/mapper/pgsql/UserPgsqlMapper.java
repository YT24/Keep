package com.keep.multdatasource.mapper.pgsql;

import com.keep.multdatasource.entity.UserPgsql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/8 16:20
 */
@Mapper
public interface UserPgsqlMapper {

    UserPgsql getById(@Param("id") Integer id);

}
