package com.keep.app.convert;

import com.keep.app.convert.entity.UserTo;
import com.keep.app.convert.entity.UserToDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 实体转换
 */
@Mapper
public interface ObjConvertMapper {

    ObjConvertMapper INSTANCE = Mappers.getMapper(ObjConvertMapper.class);

    @Mapping(source = "username",target = "name")
    UserTo toUserTo(UserToDto userToDto);

}
