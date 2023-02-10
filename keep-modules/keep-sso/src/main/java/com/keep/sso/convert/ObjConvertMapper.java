package com.keep.sso.convert;

import com.keep.sso.entity.SysUser;
import com.keep.sso.entity.param.SysUserParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 实体转换
 */
@Mapper
public interface ObjConvertMapper {

    ObjConvertMapper INSTANCE = Mappers.getMapper(ObjConvertMapper.class);

    //@Mapping(source = "username",target = "name")
    SysUser toSrs(SysUserParam userToDto);

}
