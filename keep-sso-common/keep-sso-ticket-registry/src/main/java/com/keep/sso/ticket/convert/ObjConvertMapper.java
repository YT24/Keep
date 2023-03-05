package com.keep.sso.ticket.convert;

import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.sso.ticket.entity.KeepUser;
import com.keep.sso.ticket.entity.param.SysUserParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 实体转换
 */
@Mapper
public interface ObjConvertMapper {

    ObjConvertMapper INSTANCE = Mappers.getMapper(ObjConvertMapper.class);

    //@Mapping(source = "username",target = "name")
    KeepUser toSrs(SysUserParam userToDto);

    UserInfoVo toUserInfoVo(KeepUser keepUser);

}
