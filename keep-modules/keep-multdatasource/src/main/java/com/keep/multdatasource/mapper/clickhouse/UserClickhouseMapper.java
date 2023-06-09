package com.keep.multdatasource.mapper.clickhouse;

import com.keep.multdatasource.entity.UserClickhouse;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangte
 * @description TODO
 * @date 2023/6/8 16:20
 */
@Mapper
public interface UserClickhouseMapper {

    UserClickhouse getById(Integer id);

}
