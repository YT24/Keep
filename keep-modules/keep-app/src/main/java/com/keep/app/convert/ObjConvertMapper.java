package com.keep.app.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 实体转换
 */
@Mapper
public interface ObjConvertMapper {

    ObjConvertMapper INSTANCE = Mappers.getMapper(ObjConvertMapper.class);


}
